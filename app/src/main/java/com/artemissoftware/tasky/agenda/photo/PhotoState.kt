package com.artemissoftware.tasky.agenda.photo

import com.artemissoftware.tasky.agenda.domain.models.Photo

data class PhotoState(
    val isLoading: Boolean = false,
    val photo: Photo? = null
)
