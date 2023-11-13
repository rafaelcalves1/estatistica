package com.estudos.estatistica.model

import android.os.Parcelable
import com.estudos.estatistica.ui.features.home.model.ActionHome
import kotlinx.parcelize.Parcelize

@Parcelize
data class Calculo(
    val mediaGeral: String,
    val mediana: String,
    val varianca: String,
    val fac: String,
    val dados: List<Dados>,
    val type: ActionHome
) : Parcelable