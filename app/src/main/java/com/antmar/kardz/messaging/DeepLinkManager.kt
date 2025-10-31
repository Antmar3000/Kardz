package com.antmar.kardz.messaging

import android.net.Uri

object DeepLinkManager {

    private var pendingUri: Uri? = null

    fun setPendingUri (uri: Uri) {
        pendingUri = uri
    }

    fun getPendingUri () : Uri? {
        return pendingUri.also { pendingUri = null }
    }
}