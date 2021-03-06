package com.estudos.estatistica.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dados(
    val classes: Classes?= null,
    val numero: Float? = null,
    val frequencia: Int,
    val intervalo: Float? = null,
    val mediaDado: Float? = null,
    val type: Int
) : Parcelable
