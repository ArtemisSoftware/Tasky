package com.artemissoftware.core.util.extensions

import android.content.Context
import androidx.datastore.dataStore
import com.artemissoftware.core.data.local.serializer.UserStoreSerializer

val Context.userStore by dataStore(
    fileName = "user-store.json",
    serializer = UserStoreSerializer
)
