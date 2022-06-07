package com.estudos.estatistica.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Calculo(
    val mediaGeral: String,
    val mediana: String,
    val varianca: String,
    val fac: String,
    val dados: List<Dados>,
    val type: Int
) : Parcelable