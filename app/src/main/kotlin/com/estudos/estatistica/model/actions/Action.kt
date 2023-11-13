package com.estudos.estatistica.model.actions

data class Action(
    val name: String,
    val action: ActionHome
)

enum class ActionHome(val value: String) {
    CONTINUOUS_DATA("CONTINUOUS_DATA"),
    DISCRETE_DATA("DISCRETE_DATA"),
    UNGROUPED_DISCRETE_DATA("DISCRETE_DATA"),
    HISTORIC("HISTORIC")
}
