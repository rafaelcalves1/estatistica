package com.estudos.estatistica.ui.features.calculate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estudos.estatistica.model.CalculateType
import com.estudos.estatistica.model.actions.ActionHome
import com.estudos.estatistica.model.actions.toCalculateType
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class CalculateViewModel(
    private val actionHome: String
) : ViewModel() {
    val calculateType: CalculateType
        get() = ActionHome.valueOf(actionHome).toCalculateType()

    fun requestCalculate(value: String) {
        viewModelScope.launch {
            kotlin.run {
                verifyValue(value)
            }
            val splitedList = value.split(",")
        }
    }

    private fun verifyValue(value: String) {
        if (value.contains(",").not()) throw IllegalArgumentException()
    }
}