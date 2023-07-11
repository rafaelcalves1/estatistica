package com.estudos.estatistica.model

data class Action(
    val name: String,
    val action: ActionHome
)

enum class ActionHome {
    CONTINUOUS_DATA, DISCRETE_DATA, UNGROUPED_DISCRETE_DATA
}
