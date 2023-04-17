package com.artemissoftware.tasky.agenda.domain.compressor

interface ImageCompressor {
    suspend fun compressUri(uri: String): ByteArray
}
