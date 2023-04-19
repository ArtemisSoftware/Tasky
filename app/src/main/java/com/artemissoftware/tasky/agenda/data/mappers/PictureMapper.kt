package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.core.data.database.entities.PictureEntity
import com.artemissoftware.core.data.database.types.PictureType
import com.artemissoftware.tasky.agenda.data.remote.dto.PhotoDto
import com.artemissoftware.tasky.agenda.domain.models.Picture

fun PictureEntity.toPicture(): Picture {
    return when (this.type) {
        PictureType.REMOTE -> {
            Picture.Remote(key = id, url = source)
        }
        PictureType.LOCAL -> {
            Picture.Local(picId = id, uri = source)
        }
    }
}

fun PhotoDto.toEntity(eventId: String): PictureEntity {
    return PictureEntity(
        eventId = eventId,
        source = url,
        id = key,
        type = PictureType.REMOTE,
    )
}

fun Picture.toEntity(eventId: String): PictureEntity {
    return when (this) {
        is Picture.Local -> {
            PictureEntity(
                eventId = eventId,
                source = uri,
                id = picId,
                type = PictureType.LOCAL,
            )
        }
        is Picture.Remote -> {
            PictureEntity(
                eventId = eventId,
                source = url,
                id = key,
                type = PictureType.REMOTE,
            )
        }
    }
}

fun PhotoDto.toPicture(): Picture {
    return Picture.Remote(
        key = this.key,
        url = this.url,
    )
}
