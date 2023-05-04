package com.artemissoftware.core.util.extensions

import android.net.Uri

fun Uri.replaceUriParameter(key: String, newValue: String): Uri {
    val params: Set<String> = this.queryParameterNames
    val newUri: Uri.Builder = this.buildUpon().clearQuery()
    for (param in params) {
        newUri.appendQueryParameter(
            param,
            if (param == key) newValue else this.getQueryParameter(param),
        )
    }
    return newUri.build()
}
