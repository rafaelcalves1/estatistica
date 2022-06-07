package com.estudos.estatistica.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Classes(
    val limiteInferior: Float,
    val limiteSuperior: Float
) : Parcelable