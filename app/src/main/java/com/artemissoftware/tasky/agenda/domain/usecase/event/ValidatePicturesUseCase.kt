package com.artemissoftware.tasky.agenda.domain.usecase.event

import com.artemissoftware.core.domain.AgendaException
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.util.UiText
import com.artemissoftware.core.util.constants.ImageSizeConstants
import com.artemissoftware.tasky.agenda.domain.compressor.ImageCompressor
import com.artemissoftware.tasky.agenda.domain.models.Picture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidatePicturesUseCase @Inject constructor(
    private val imageCompressor: ImageCompressor,
) {
    suspend operator fun invoke(pictures: List<Picture>): Flow<Resource<List<Picture>>> = flow {
        var rejectedPictures = 0

        val list = mutableListOf<Picture>()

        pictures.filterIsInstance<Picture.Local>().forEach { picture ->

            val imageSize = imageCompressor.compressUri(uri = picture.uri).size

            if (imageSize > ImageSizeConstants.ONE_MEGA_BYTE) {
                ++rejectedPictures
            }
            else {
                list.add(picture)
            }
        }

        if (rejectedPictures != 0) {
            emit(Resource.Error(exception = AgendaException.NotValidPictures(UiText.DynamicString("$rejectedPictures photos were skipped because they were too large"))))
        }

        emit(Resource.Success(list))
    }
}
