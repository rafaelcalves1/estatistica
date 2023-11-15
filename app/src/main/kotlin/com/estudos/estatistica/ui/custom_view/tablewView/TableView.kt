package com.estudos.estatistica.ui.custom_view.tablewView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estudos.estatistica.databinding.LayoutTableViewBinding

class TableView(
    context: Context,
    attrs: AttributeSet
) : RecyclerView(
    context, attrs
) {

    private val binding: LayoutTableViewBinding = LayoutTableViewBinding.inflate(
        LayoutInflater.from(context)
    )

    private val tableViewAdapter = TableViewAdapter()

    fun submitList(
        list: List<String>
    ) {
        tableViewAdapter.submitList(list)
    }

    fun build(
        numberOfColumns: Int
    ) {
        layoutManager = GridLayoutManager(context, numberOfColumns)
        adapter = tableViewAdapter
    }
}