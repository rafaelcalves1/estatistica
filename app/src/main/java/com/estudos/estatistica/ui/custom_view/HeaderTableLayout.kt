package com.estudos.estatistica.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.estudos.estatistica.databinding.LayoutHeaderTableBinding

class HeaderTableLayout(
    context: Context,
    attrs: AttributeSet
) : LinearLayoutCompat(
    context, attrs
) {

    private val binding: LayoutHeaderTableBinding = LayoutHeaderTableBinding.inflate(
        LayoutInflater.from(context), this
    )

    init {
        showFac(true)
        showXifi(true)
        showXi(true)
        setTextClasses("Classes")
    }

    fun showFac(boolean: Boolean) {
        binding.fac.visibility = if (!boolean) View.GONE else View.VISIBLE
    }

    fun showXifi(boolean: Boolean) {
        binding.xifi.visibility = if (!boolean) View.GONE else View.VISIBLE
    }

    fun showXi(boolean: Boolean){
        binding.media.visibility = if (!boolean) View.GONE else View.VISIBLE
    }

    fun setTextClasses(text: String){
        binding.classes.text = text
    }
}