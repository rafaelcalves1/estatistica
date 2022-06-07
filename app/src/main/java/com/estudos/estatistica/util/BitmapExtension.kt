package com.estudos.estatistica.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.widget.NestedScrollView

fun NestedScrollView.getBitmapFromView(): Bitmap {
    val bitmap = Bitmap.createBitmap(getChildAt(0).width, getChildAt(0).height, Bitmap.Config.ARGB_8888)
    bitmap.eraseColor(Color.WHITE)
    getChildAt(0).draw(Canvas(bitmap))
    return bitmap
}