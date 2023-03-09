package com.artemissoftware.core.util.extensions

import android.content.Context
import androidx.datastore.dataStore
import com.artemissoftware.core.data.local.serializer.UserStoreSerializer
import com.artemissoftware.core.data.managers.CryptoManager

val Context.userStore by dataStore(
    fileName = "user-store.json",
    serializer = UserStoreSerializer(CryptoManager())
)
