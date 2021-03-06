package com.estudos.estatistica.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.estudos.estatistica.databinding.TabelaBinding
import com.estudos.estatistica.model.Dados
import com.estudos.estatistica.ui.fragment.DADOS_CONTINUOS
import com.estudos.estatistica.ui.fragment.DADOS_DISCRETOS_AGRUPADOS
import com.estudos.estatistica.ui.fragment.DADOS_DISCRETOS_NAO_AGRUPADOS
import kotlin.math.roundToInt

class DadosAdapter: RecyclerView.Adapter<DadosViewHolder>() {

    private val listOfData = mutableListOf<Dados>()

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

    fun setList(dados: Dados){
        listOfData.add(dados)
        notifyItemInserted(listOfData.lastIndex)
    }
}

class DadosViewHolder(binding: TabelaBinding): RecyclerView.ViewHolder(binding.root){

    private val classes =  binding.classes
    private val frequencias =  binding.frequencias
    private val media =  binding.media
    private val fac =  binding.fac
    private val xifi =  binding.xifi

    fun bind(dados: Dados){
        fac.visibility = View.GONE
        media.visibility = View.GONE
        xifi.visibility = View.GONE
        frequencias.text = dados.frequencia.toString()
        setClasses(dados)
        setPontoMedio(dados)
    }

    private fun setPontoMedio(dados: Dados){
        media.text = dados.mediaDado.toString()
    }

    private fun setClasses(dados: Dados){
        when(dados.type){
            DADOS_CONTINUOS -> {
                classes.text = "${dados.classes!!.limiteInferior} |- ${dados.classes!!.limiteSuperior}"
            }
            DADOS_DISCRETOS_AGRUPADOS -> {
                classes.text = dados.numero!!.roundToInt().toString()
            }
            DADOS_DISCRETOS_NAO_AGRUPADOS -> {
                classes.text = dados.numero!!.roundToInt().toString()
            }
        }
    }
}