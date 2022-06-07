package com.estudos.estatistica.util

import android.view.View
import kotlin.math.absoluteValue

fun String.validateViewToVisibleOrGone(view: View){
    if(this.isNullOrBlank() || this.isNullOrEmpty()){
        view.visibility = View.GONE
    }else{
        view.visibility = View.VISIBLE
    }
}

fun String.isBlankOrEmpty(): Boolean{
    return this.isBlank() || this.isEmpty()
}

fun Float.format(digits: Int) = "%.${digits}f".format(this)

fun Float.aoQuadrado(): Float {
    val quadrado = this * this
    return quadrado
}