package com.estudos.estatistica.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.estudos.estatistica.R
import com.estudos.estatistica.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    lateinit var navController: NavController

    private lateinit var btnDadosDiscretosAg : AppCompatButton
    private lateinit var  btnDadosDiscretosNaoAg : AppCompatButton
    private lateinit var  btnContinuos : AppCompatButton

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnDadosDiscretosAg =  binding.btnDadosDiscretosAgrupados
        btnDadosDiscretosNaoAg = binding.btnDadosDiscretosNaoAgrupados
        btnContinuos = binding.btnDadosContinuos

        configureListenners()
    }


    private fun configureListenners() {
        btnContinuos.setOnClickListener {
            navController.navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                DADOS_CONTINUOS))
        }

        btnDadosDiscretosAg.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                DADOS_DISCRETOS_AGRUPADOS)
            navController.navigate(action)
        }

        btnDadosDiscretosNaoAg.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                DADOS_DISCRETOS_NAO_AGRUPADOS)
            navController.navigate(action)
        }
    }
}