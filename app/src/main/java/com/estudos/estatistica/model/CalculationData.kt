package com.estudos.estatistica.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalculationData(
    val mediaGeral: Float,
    val mediana: Float,
    val varianca: Float,
    val fac: String,
    val dataForCalculation: List<DataForCalculations>,
    val type: ActionHome
) : Parcelable