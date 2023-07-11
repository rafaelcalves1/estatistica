package com.estudos.estatistica.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.estudos.estatistica.R
import com.estudos.estatistica.databinding.TabelaBinding
import com.estudos.estatistica.model.DataForCalculations

class FullDataAdapter(private val list: List<DataForCalculations>) :
    RecyclerView.Adapter<FullDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullDataViewHolder {
        val binding = TabelaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FullDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FullDataViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}

class FullDataViewHolder(private val binding: TabelaBinding) :
    RecyclerView.ViewHolder(binding.root) {
    
    // region Views
    private val labelVariables
        get() = binding.classes
    
    private val labelFrequency
        get() = binding.frequencias
    
    private val labelAverage
        get() = binding.media
    
    private val labelCumalativeFrequency
        get() = binding.fac

    private val labelXiFi
        get() = binding.xifi
    //endregion

    fun bind(dataForCalculation: DataForCalculations) {
        when (dataForCalculation) {
            is DataForCalculations.ContinuousData -> {
                setupContinousData(dataForCalculation)
            }
            is DataForCalculations.DiscreteData -> {
                setupDiscreteData(dataForCalculation)
            }
            is DataForCalculations.UngroupedDiscreteData -> {
                setupUngroupedDiscreteData(dataForCalculation)
            }
        }
    }
    
    private fun setupUngroupedDiscreteData(data: DataForCalculations.UngroupedDiscreteData) {
        labelAverage.visibility = View.GONE
        labelXiFi.visibility = View.GONE
        labelCumalativeFrequency.visibility = View.GONE
        labelFrequency.text = data.frequencia.toString()
        labelVariables.text = data.numero.toString()
    }

    private fun setupDiscreteData(data: DataForCalculations.DiscreteData) {
        labelAverage.visibility = View.GONE
        labelXiFi.text = (data.numero.times(data.frequencia)).toString()
        labelCumalativeFrequency.text = labelCumalativeFrequency.toString()
        labelFrequency.text = data.frequencia.toString()
        labelVariables.text = data.numero.toString()
    }

    private fun setupContinousData(data: DataForCalculations.ContinuousData) {
        labelCumalativeFrequency.text = labelCumalativeFrequency.toString()
        labelFrequency.text = data.frequencia.toString()
        labelVariables.text = itemView.context.getString(
            R.string.table_class,
            data.classes.limiteInferior.toString(),
            data.classes.limiteSuperior.toString()
        )
        configureXiAndXiFi(data)
    }

    private fun configureXiAndXiFi(data: DataForCalculations.ContinuousData) {
        val xi = (data.classes.limiteInferior + data.classes.limiteSuperior) / 2
        labelAverage.text = xi.toString()
        labelXiFi.text = xi.times(data.frequencia).toString()
    }

}