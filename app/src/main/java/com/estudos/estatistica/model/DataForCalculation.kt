package com.estudos.estatistica.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataForCalculation(
    val classes: Classes?= null,
    val numero: Float? = null,
    val frequencia: Int,
    val intervalo: Float? = null,
    val mediaDado: Float? = null,
    val fac: Int = 0,
) : Parcelable

@Parcelize

sealed class DataForCalculations : Parcelable {

    data class ContinuousData(
        val classes: Classes,
        val frequencia: Int,
        val mediaDado: Float? = null,
        val fac: Int = 0,
        val type: ActionHome
    ): DataForCalculations()

    data class DiscreteData(
        val numero: Float,
        val frequencia: Int,
        val intervalo: Float? = null,
        val mediaDado: Float? = null,
        val fac: Int = 0,
    ): DataForCalculations()

    data class UngroupedDiscreteData(
        val numero: Float? = null,
        val frequencia: Int,
        val intervalo: Float? = null,
        val mediaDado: Float? = null,
        val fac: Int = 0,
    ): DataForCalculations()
}
