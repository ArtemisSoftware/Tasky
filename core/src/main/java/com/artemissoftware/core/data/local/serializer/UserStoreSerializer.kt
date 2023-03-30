package com.artemissoftware.core.data.local.serializer

import androidx.datastore.core.Serializer
import com.artemissoftware.core.data.local.models.UserStore
import com.artemissoftware.core.data.managers.CryptoManager
import com.artemissoftware.core.data.mappers.toUser
import com.artemissoftware.core.data.mappers.toUserStore
import com.artemissoftware.core.domain.models.authentication.User
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class UserStoreSerializer(private val cryptoManager: CryptoManager) : Serializer<User> {

    override val defaultValue: User
        get() = User()

    override suspend fun readFrom(input: InputStream): User {
        val decryptedBytes = if (USE_CRYPTO) cryptoManager.decrypt(input) else input.readBytes()

        return try {
            Json.decodeFromString(
                deserializer = UserStore.serializer(),
                string = decryptedBytes.decodeToString(),
            ).toUser()
        } catch (e: SerializationException) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: User, output: OutputStream) {
        val bytes = Json.encodeToString(
            serializer = UserStore.serializer(),
            value = t.toUserStore(),
        )

        if (USE_CRYPTO) {
            cryptoManager.encrypt(
                bytes = bytes.encodeToByteArray(),
                outputStream = output,
            )
        } else {
            output.write(bytes.encodeToByteArray())
        }
    }

    companion object {
        private const val USE_CRYPTO = false // TODO: only for testing. Crypto is not working. Will pick this up later
    }
}
