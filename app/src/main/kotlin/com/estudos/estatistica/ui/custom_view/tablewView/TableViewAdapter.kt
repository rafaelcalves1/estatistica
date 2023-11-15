package com.estudos.estatistica.ui.custom_view.tablewView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.estudos.estatistica.databinding.LayoutTableItemViewBinding

class TableViewAdapter() : ListAdapter<String, TableViewViewHolder>(DiffUtilTableView()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewViewHolder {
        val binding = LayoutTableItemViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TableViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TableViewViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}

private class DiffUtilTableView : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class TableViewViewHolder(
    private val binding: LayoutTableItemViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(value: String) {
        binding.root.text = value
    }
}