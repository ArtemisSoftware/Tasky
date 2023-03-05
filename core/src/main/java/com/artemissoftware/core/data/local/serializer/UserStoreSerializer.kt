package com.artemissoftware.core.data.local.serializer

import androidx.datastore.core.Serializer
import com.artemissoftware.core.data.local.models.UserStore
import com.artemissoftware.core.data.mappers.toUser
import com.artemissoftware.core.data.mappers.toUserStore
import com.artemissoftware.core.domain.models.authentication.User
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserStoreSerializer : Serializer<User> {

    override val defaultValue: User
        get() = UserStore().toUser()


    override suspend fun readFrom(input: InputStream): User {
        return try {
            Json.decodeFromString(
                deserializer = UserStore.serializer(),
                string = input.readBytes().decodeToString()
            ).toUser()
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }


    override suspend fun writeTo(user: User, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = UserStore.serializer(),
                value = user.toUserStore()
            ).encodeToByteArray()
        )
    }
}