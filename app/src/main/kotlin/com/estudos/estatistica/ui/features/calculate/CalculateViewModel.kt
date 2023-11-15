package com.estudos.estatistica.ui.features.calculate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estudos.estatistica.model.CalculateData
import com.estudos.estatistica.model.CalculateType
import com.estudos.estatistica.model.actions.ActionHome
import com.estudos.estatistica.model.actions.toCalculateType
import kotlinx.coroutines.launch
import java.lang.IllegalStateException


class CalculateViewModel(
    private val actionHome: String
) : ViewModel() {

    val calculateType: CalculateType
        get() = ActionHome.valueOf(actionHome).toCalculateType()

    private val INITIAL_LIST = listOf("Classes", "Fi")
    val numberOfRows = INITIAL_LIST.size


    private val _currentList = MutableLiveData<List<String>>(INITIAL_LIST)
    val currentList: LiveData<List<String>>
        get() = _currentList

    private val listOfCalculateDiscreteData = mutableListOf<CalculateData>()

    private val _currentNumberOfValues = MutableLiveData<Int>(0)
    val currentNumberOfValues: LiveData<Int>
        get() = _currentNumberOfValues

    private val _error = MutableLiveData<Unit>()
    val error: LiveData<Unit>
        get() = _error

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

    fun addValueToList(nameOfclass: String, frequency: String) {
        viewModelScope.launch {
            try {
                if (nameOfclass.isNullOrEmpty()) throw IllegalArgumentException()

                val newFrequency = frequency.toInt()

                val newList = listOf(nameOfclass, newFrequency.toString())

                addToCalculateData(nameOfclass, newFrequency)

                val currentMutableList =
                    currentList.value?.toMutableList() ?: throw IllegalStateException()

                currentMutableList.addAll(newList)

                _currentList.value = currentMutableList
            } catch (ex: Exception) {
                _error.value = Unit
            }
        }
    }


    private fun addToCalculateData(nameOfclass: String, frequency: Int = 1) {
        val newCalculateData = CalculateData(nameClass = nameOfclass, frequency = frequency)
        listOfCalculateDiscreteData.add(newCalculateData)
        _currentNumberOfValues.value = listOfCalculateDiscreteData.size
    }
}