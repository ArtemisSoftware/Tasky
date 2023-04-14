package com.artemissoftware.tasky.agenda.data.compressor

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.artemissoftware.core.util.constants.ImageSizeConstants.COMPRESSION_QUALITY
import com.artemissoftware.tasky.agenda.domain.compressor.ImageCompressor
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ImageCompressorImpl @Inject constructor(
    private val context: Context,
) : ImageCompressor {

    override suspend fun compressUri(uri: String): ByteArray {
        val bytes = context.contentResolver.openInputStream(Uri.parse(uri))?.use {
            it.readBytes()
        } ?: byteArrayOf()

        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_QUALITY, outputStream)

        return outputStream.toByteArray()
    }
}
