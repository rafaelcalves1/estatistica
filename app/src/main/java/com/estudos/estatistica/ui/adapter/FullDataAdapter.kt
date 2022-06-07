package com.estudos.estatistica.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.estudos.estatistica.databinding.TabelaBinding
import com.estudos.estatistica.model.Dados
import com.estudos.estatistica.ui.fragment.DADOS_CONTINUOS
import com.estudos.estatistica.ui.fragment.DADOS_DISCRETOS_AGRUPADOS
import com.estudos.estatistica.ui.fragment.DADOS_DISCRETOS_NAO_AGRUPADOS

class FullDataAdapter(private val list: List<Dados>, private val type: Int): RecyclerView.Adapter<FullDataViewHolder>() {

    var fac = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullDataViewHolder {
        val binding = TabelaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FullDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FullDataViewHolder, position: Int) {
            fac += list[position].frequencia
            holder.bind(list[position], fac, type)
    }

    override fun getItemCount(): Int  = list.size

}

class FullDataViewHolder(binding: TabelaBinding): RecyclerView.ViewHolder(binding.root){

    private val classes = binding.classes
    private val frequencia = binding.frequencias
    private val media = binding.media
    private val fac = binding.fac
    private val xifi = binding.xifi

    fun bind(dados: Dados, fac: Int, type: Int){
        setClasses(dados)
        when(type){
            DADOS_CONTINUOS -> {
                configureXiAndXiFi(dados)
                this.fac.text = fac.toString()
            }
            DADOS_DISCRETOS_AGRUPADOS -> {
                media.visibility = View.GONE
                xifi.text = (dados.numero?.times(dados.frequencia)).toString()
                this.fac.text = fac.toString()
            }
            DADOS_DISCRETOS_NAO_AGRUPADOS -> {
                media.visibility = View.GONE
                xifi.visibility = View.GONE
                this.fac.visibility = View.GONE
            }
        }
        frequencia.text = dados.frequencia.toString()

    }


    @SuppressLint("SetTextI18n")
    private fun configureXiAndXiFi(dados: Dados){
        val xi = (dados.classes!!.limiteInferior + dados.classes.limiteSuperior)/2
        media.text = xi.toString()
        xifi.text = xi.times(dados.frequencia).toString()
    }

    @SuppressLint("SetTextI18n")
    private fun setClasses(dados: Dados){
        when(dados.type){
            DADOS_CONTINUOS -> {
                classes.text = "${dados.classes!!.limiteInferior} |- ${dados.classes.limiteSuperior}"
            }
            DADOS_DISCRETOS_AGRUPADOS -> {
                classes.text = dados.numero.toString()
            }
            DADOS_DISCRETOS_NAO_AGRUPADOS -> {
                classes.text = dados.numero.toString()
            }
        }
    }
}