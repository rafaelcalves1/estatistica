package com.estudos.estatistica.util

import android.content.Context
import android.content.Intent
import android.net.Uri

private const val IMG_TYPE = "image/png"

fun Context.shareImage(uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.type = IMG_TYPE
    startActivity(intent)
}