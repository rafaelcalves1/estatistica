package com.estudos.estatistica.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.estudos.estatistica.databinding.TabelaBinding
import com.estudos.estatistica.model.ActionHome
import com.estudos.estatistica.model.DataForCalculation
import kotlin.math.roundToInt

class DadosAdapter: RecyclerView.Adapter<DadosViewHolder>() {

    private val listOfData = mutableListOf<DataForCalculation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DadosViewHolder {
        val binding = TabelaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DadosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DadosViewHolder, position: Int) {
        holder.bind(listOfData[position])
    }

    override fun getItemCount(): Int = listOfData.size

    fun setList(dataForCalculation: DataForCalculation){
        listOfData.add(dataForCalculation)
        notifyItemInserted(listOfData.lastIndex)
    }
}

class DadosViewHolder(binding: TabelaBinding): RecyclerView.ViewHolder(binding.root){

    private val classes =  binding.classes
    private val frequencias =  binding.frequencias
    private val media =  binding.media
    private val fac =  binding.fac
    private val xifi =  binding.xifi

    fun bind(dataForCalculation: DataForCalculation){
        fac.visibility = View.GONE
        media.visibility = View.GONE
        xifi.visibility = View.GONE
        frequencias.text = dataForCalculation.frequencia.toString()
        setClasses(dataForCalculation)
        setPontoMedio(dataForCalculation)
    }

    private fun setPontoMedio(dataForCalculation: DataForCalculation){
        media.text = dataForCalculation.mediaDado.toString()
    }

    private fun setClasses(dataForCalculation: DataForCalculation){
        when(dataForCalculation.type){
            ActionHome.CONTINUOUS_DATA -> {
                classes.text = "${dataForCalculation.classes!!.limiteInferior} |- ${dataForCalculation.classes!!.limiteSuperior}"
            }
            ActionHome.DISCRETE_DATA -> {
                classes.text = dataForCalculation.numero!!.roundToInt().toString()
            }
            ActionHome.UNGROUPED_DISCRETE_DATA -> {
                classes.text = dataForCalculation.numero!!.roundToInt().toString()
            }
            else -> {}
        }
    }
}