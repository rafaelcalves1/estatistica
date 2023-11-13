package com.estudos.estatistica.ui.custom_view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.estudos.estatistica.R
import com.estudos.estatistica.databinding.DialogCustomBinding
import com.estudos.estatistica.util.isBlankOrEmpty

class DialogCustom(private val titleText: String): DialogFragment() {

    private var _binding: DialogCustomBinding? = null
    private val binding get() = _binding!!

    lateinit var function: (Int) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCustomBinding.inflate(inflater, container, false)
        this@DialogCustom.dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitleText()
        binding.dialogBtnConfirm.setOnClickListener {
            if(binding.editTextValor.text.toString().isBlankOrEmpty()){
                Toast.makeText(context, "Informe um valor para continuar!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            function(binding.editTextValor.text.toString().toInt())
            dismiss()
        }
    }

    private fun setTitleText(){
        binding.dialogTxtInfo.text = getString(R.string.dialog_info, titleText)
    }

    fun setOnClickListenner(function: (Int) -> Unit){
        this.function = function
    }
}