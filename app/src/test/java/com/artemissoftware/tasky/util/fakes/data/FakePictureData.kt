package com.artemissoftware.tasky.util.fakes.data

import com.artemissoftware.core.data.database.entities.PictureEntity
import com.artemissoftware.core.data.database.types.PictureType
import com.artemissoftware.tasky.agenda.data.remote.dto.PhotoDto
import com.artemissoftware.tasky.agenda.domain.models.Picture

object FakePictureData {

    val pictureDto = PhotoDto(
        key = "122REMOTE",
        url = "http://www.batman.com/catwoman.jpg",
    )

    val pictureEntityRemote = PictureEntity(
        eventId = "eventId",
        id = "122REMOTE",
        source = "http://www.batman.com/catwoman.jpg",
        type = PictureType.REMOTE,
    )

    val pictureEntityLocal = PictureEntity(
        eventId = "eventId",
        id = "122LOCAL",
        source = "content//www.pic.catwoman",
        type = PictureType.LOCAL,
    )

    val pictureRemote = Picture.Remote(
        key = "122REMOTE",
        url = "http://www.batman.com/catwoman.jpg",
    )

    val pictureLocal = Picture.Local(
        picId = "122LOCAL",
        uri = "content//www.pic.catwoman",
    )

    val pictureEntities = listOf(pictureEntityRemote, pictureEntityLocal)
    val pictures = listOf(pictureRemote, pictureLocal)
}
