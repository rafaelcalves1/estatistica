package com.estudos.estatistica.ui.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.estudos.estatistica.databinding.ActionsHomeItemBinding
import com.estudos.estatistica.model.actions.Action

class HomeAdapter : ListAdapter<Action, HomeViewHolder>(HomeActionDiffUtil()) {

    private var listener: ((Action) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ActionsHomeItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    fun setOnClickListener(listener: ((Action) -> Unit)) {
        this.listener = listener
    }

}

class HomeViewHolder(binding: ActionsHomeItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val labelName = binding.rootView

    fun bind(item: Action, setOnClickListener: ((Action) -> Unit)?) {
        labelName.text = item.name
        labelName.setOnClickListener {
            setOnClickListener?.let { listener ->
                listener.invoke(item)
            }
        }
    }

}

class HomeActionDiffUtil : DiffUtil.ItemCallback<Action>() {
    override fun areItemsTheSame(oldItem: Action, newItem: Action): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Action, newItem: Action): Boolean {
        return oldItem == newItem
    }

}