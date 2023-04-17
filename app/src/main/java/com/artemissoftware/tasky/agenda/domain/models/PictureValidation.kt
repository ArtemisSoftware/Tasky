package com.artemissoftware.tasky.agenda.domain.models

data class PictureValidation(
    val numberOfRejectedPictures: Int = 0,
    val validPictures: List<Picture>,
)
