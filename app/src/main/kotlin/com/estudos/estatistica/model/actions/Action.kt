package com.estudos.estatistica.model.actions

import com.estudos.estatistica.model.CalculateType

data class Action(
    val name: String,
    val action: ActionHome
)

enum class ActionHome(val value: String) {
    CONTINUOUS_DATA("CONTINUOUS_DATA"),
    DISCRETE_DATA("DISCRETE_DATA"),
    GROUPED_DISCRETE_DATA("GROUPED_DISCRETE_DATA"),
    HISTORIC("HISTORIC")
}

fun ActionHome.toCalculateType() =
    when(this){
        ActionHome.CONTINUOUS_DATA -> CalculateType.CONTINUOUS_DATA
        ActionHome.DISCRETE_DATA -> CalculateType.DISCRETE_DATA
        ActionHome.GROUPED_DISCRETE_DATA -> CalculateType.GROUPED_DISCRETE_DATA
        else -> CalculateType.UNKNOWN
    }