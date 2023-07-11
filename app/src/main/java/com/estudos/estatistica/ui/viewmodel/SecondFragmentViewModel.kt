package com.estudos.estatistica.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estudos.estatistica.model.ActionHome
import com.estudos.estatistica.model.CalculationData
import com.estudos.estatistica.model.DataForCalculation
import com.estudos.estatistica.util.aoQuadrado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class SecondFragmentViewModel(private val typeCacl: ActionHome) : ViewModel() {

    val typeOfCalc: ActionHome
        get() = typeCacl

    var qtdValores: Int = 0

    private val _number = MutableLiveData<String>()
    val number: LiveData<String> = _number

    private val listOfData: ArrayList<DataForCalculation> = arrayListOf()

    private val _data = MutableLiveData<DataForCalculation>()
    val data: LiveData<DataForCalculation> = _data

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _calc = MutableLiveData<Boolean>()
    val calc: LiveData<Boolean> = _calc

    private val _calculationData = MutableLiveData<CalculationData>()
    val calculationData: LiveData<CalculationData> = _calculationData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var mediaGeral = 0f
    private var fac = 0f

    fun addNumberToString(string: String) {
        if (_number.value.isNullOrEmpty()) {
            _number.value = string
            return
        }
        if (_number.value!!.length > 9) {
            _error.value = "Não é possível adicionar um numero com mais de 9 caracteres."
            return
        }
        _number.value += string
    }

    fun removeLastCharacter() {
        val newNumber = _number.value
        _number.value = newNumber?.dropLast(1)
    }

    fun addDataToList(dataForCalculation: DataForCalculation) {
        viewModelScope.launch(Dispatchers.Default) {
            val fac = if (listOfData.isEmpty()) 0 else listOfData.sumOf { it.fac }
            val newDataForCalculation = dataForCalculation.copy(fac = fac)
            listOfData.add(newDataForCalculation)
            _data.postValue(newDataForCalculation)
            _number.postValue("")
            if (listOfData.size + 1 == qtdValores) {
                _calc.postValue(true)
                return@launch
            }
            _calc.postValue( false)
        }
    }

    fun calculaLimiteSuperior(float: Float, intervalo: Float): Float {
        return float + intervalo
    }

    fun executaCalculos() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)

            val fac = calculaFAC()
            val mediaGeral = calculoMedia()
            val mediana = calculoMediana()
            val varianca = calculoVarianca()

            delay(1000)

            _loading.postValue(false)
            _calculationData.postValue(
                CalculationData(
                    fac = fac,
                    mediaGeral = mediaGeral.toFloat(),
                    mediana = mediana.toFloat(),
                    varianca = varianca.toFloat(),
                    dataForCalculation = listOfData,
                    type = typeOfCalc
                )
            )
        }
    }

    fun zeraNumero() {
        _number.value = ""
    }

    private fun calculoMediana(): String {
        var mediana = ""
        when (typeOfCalc) {
            ActionHome.CONTINUOUS_DATA -> {
                val encontrarNumero = fac / 2
                val dadoMediana = encontrarMediana(encontrarNumero)
                mediana =
                    (dadoMediana.classes!!.limiteInferior + (((encontrarNumero) / dadoMediana.frequencia) * dadoMediana.intervalo!!)).toString()
            }
            ActionHome.UNGROUPED_DISCRETE_DATA -> {
                val listXi = listXi().sorted()
                mediana = if (!isPar(listXi)) {
                    val meio = listXi.size / 2
                    listXi[meio].toString()
                } else {
                    val numeroUm = listXi[listXi.size / 2]
                    val numeroDois = listXi[(listXi.size / 2) - 1]
                    ((numeroUm + numeroDois) / 2).toString()
                }
            }
            ActionHome.DISCRETE_DATA -> {
                val listXi = listXi()
                mediana = if (!isPar(listXi)) {
                    val meio = listXi.size / 2
                    listXi[(meio)].toString()
                } else {
                    val numeroUm = listXi[listXi.size / 2]
                    val numeroDois = listXi[(listXi.size / 2) - 1]
                    ((numeroUm + numeroDois) / 2).toString()
                }
            }
        }
        return mediana
    }

    private fun calculoMedia(): String {
        when (typeOfCalc) {
            ActionHome.CONTINUOUS_DATA -> {
                val listaXIFi = listXiFi()
                val sum = listaXIFi.sum()
                mediaGeral = (sum / fac)
            }
            ActionHome.DISCRETE_DATA -> {
                var calculoNumeros: Float = 0f
                listOfData.forEach {
                    calculoNumeros += it.numero!! * it.frequencia
                }
                mediaGeral = (calculoNumeros / fac)
            }
            ActionHome.UNGROUPED_DISCRETE_DATA -> {
                var calculoNumeros: Float = 0f
                listOfData.forEach {
                    calculoNumeros += it.numero!!
                }
                mediaGeral = (calculoNumeros / listOfData.size)
            }
        }
        return mediaGeral.toString()
    }


    private fun calculoVarianca(): String {
        var varianca = ""
        when (typeOfCalc) {
            ActionHome.CONTINUOUS_DATA -> {
                val listaXI = hashMapOf<Int, Float>()
                listOfData.forEach {
                    val media = (it.classes!!.limiteInferior + it.classes.limiteSuperior) / 2
                    listaXI[it.frequencia] = media
                }

                var somatoria = 0f
                listaXI.forEach {
                    somatoria += ((it.value - mediaGeral).aoQuadrado() * it.key)
                }

                varianca = (somatoria / (fac - 1)).toString()

            }
            ActionHome.UNGROUPED_DISCRETE_DATA -> {

                val listaXI = listXi()
                val media = listaXI.sum() / listaXI.size
                var somatoria = 0f

                listaXI.forEach {
                    somatoria += (it - media).aoQuadrado()
                }

                varianca = (somatoria / (listaXI.size - 1)).toString()

            }
            ActionHome.DISCRETE_DATA -> {

                val listaXIAndFi = hashMapOf<Float, Int>()
                listOfData.forEach {
                    listaXIAndFi[it.numero!!] = it.frequencia
                }

                var somatoria = 0f

                listaXIAndFi.forEach {
                    somatoria += ((it.key - mediaGeral).aoQuadrado()) * it.value
                }

                varianca = (somatoria / (fac - 1)).toString()

            }
        }
        return varianca
    }

    private fun calculaFAC(): String {
        listOfData.forEach {
            fac += it.frequencia
        }
        return fac.toString()
    }

    private fun encontrarMediana(float: Float): DataForCalculation {
        var dataForCalculation: DataForCalculation? = null
        var contadorUm = 0
        var contadorDois = 0
        val intege = float.toInt()
        var passou: Boolean = false
        listOfData.forEach {
            contadorUm += it.frequencia
            if ((contadorDois..contadorUm).contains(intege) && !passou) {
                dataForCalculation = it
                passou = true
            }
            contadorDois = contadorUm
        }
        return dataForCalculation!!
    }

    private fun listXiFi(): ArrayList<Float> {
        val listaXIFi = arrayListOf<Float>()
        listOfData.forEach {
            val xi = (it.classes!!.limiteInferior + it.classes.limiteSuperior) / 2
            listaXIFi.add(xi * it.frequencia)
        }
        return listaXIFi
    }

    private fun listXi(): ArrayList<Float> {
        val listaXI = arrayListOf<Float>()
        listOfData.forEach {
            listaXI.add(it.numero!!)
        }
        return listaXI
    }

    private fun isPar(list: List<Float>): Boolean {
        return list.size % 2 == 0
    }
}
