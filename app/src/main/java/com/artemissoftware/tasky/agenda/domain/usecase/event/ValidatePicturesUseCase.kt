package com.artemissoftware.tasky.agenda.domain.usecase.event

import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.util.constants.ImageSizeConstants
import com.artemissoftware.tasky.agenda.domain.compressor.ImageCompressor
import com.artemissoftware.tasky.agenda.domain.models.Picture
import com.artemissoftware.tasky.agenda.domain.models.PictureValidation
import javax.inject.Inject

class ValidatePicturesUseCase @Inject constructor(
    private val imageCompressor: ImageCompressor,
) {
    suspend operator fun invoke(pictures: List<Picture>): Resource<PictureValidation> {
        var rejectedPictures = 0

        val list = mutableListOf<Picture>()

        pictures.filterIsInstance<Picture.Local>().forEach { picture ->

            val imageSize = imageCompressor.compressUri(uri = picture.uri).size

            if (imageSize > ImageSizeConstants.ONE_MEGA_BYTE) {
                ++rejectedPictures
            } else {
                list.add(picture)
            }
        }

        return Resource.Success(
            PictureValidation(numberOfRejectedPictures = rejectedPictures, validPictures = list),
        )
    }
}
