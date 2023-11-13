package com.estudos.estatistica.ui.fragment

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.estudos.estatistica.NavGraphTableArgs
import com.estudos.estatistica.R
import com.estudos.estatistica.databinding.FragmentTableBinding
import com.estudos.estatistica.model.actions.ActionHome
import com.estudos.estatistica.model.Dados
import com.estudos.estatistica.ui.features.home.HomeActivity
import com.estudos.estatistica.ui.adapter.FullDataAdapter
import com.estudos.estatistica.ui.viewmodel.TableFragmentViewModel
import com.estudos.estatistica.util.format
import com.estudos.estatistica.util.getBitmapFromView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TableFragment : Fragment() {

    private val args by navArgs<NavGraphTableArgs>()

    private val viewModel: TableFragmentViewModel by viewModel {
        parametersOf(args.calculo)
    }

    private var _binding: FragmentTableBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        when(args.calculo.type){
            ActionHome.DISCRETE_DATA -> {
                binding.tabelaHeader.showXi(false)
            }
            ActionHome.UNGROUPED_DISCRETE_DATA -> {
                binding.tabelaHeader.showXi(false)
                binding.tabelaHeader.showXifi(false)
                binding.tabelaHeader.showFac(false)
            }
            else -> {}
        }
        addObservers()
        addListenners()
    }

    private fun configRecycler(list: List<Dados>) {
        binding.tabelaRecycler.adapter = FullDataAdapter(list)
    }

    private fun addListenners() {
        binding.toolbarBtnClose.setOnClickListener {
            activity?.run {
                Intent(this, HomeActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
            }
        }
        binding.toolbarBtnShare.setOnClickListener {
            getPrintScrenn()
        }
    }

    private fun addObservers() {
        viewModel.modelCalculo.observe(viewLifecycleOwner){
            binding.tabelaHeader.setTextClasses(setType(it.type))
            binding.mediaGeral.text = getString(R.string.tabela_media,  it.mediaGeral.toFloat().format(2))
            binding.mediana.text = getString(R.string.tabela_mediana, it.mediana.toFloat().format(2))
            binding.varianca.text = getString(R.string.tabela_varianca, it.varianca.toFloat().format(2))
            configRecycler(it.dados)
        }

        viewModel.uri.observe(viewLifecycleOwner){
            if (it != null){
                shareImageUri(it)
                return@observe
            }
            Toast.makeText(context, "Ocorreu um erro. Tente novamente", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setType(type: ActionHome): String{
       return when (type) {
           ActionHome.CONTINUOUS_DATA -> {
                "Classes"
            }
           ActionHome.DISCRETE_DATA -> {
                "Valores"
            }
           ActionHome.UNGROUPED_DISCRETE_DATA -> {
                "Valores"
            }
           else -> {
               ""
           }
        }
    }

    private fun getPrintScrenn(){
        val bitmap = binding.nestedScrollView.getBitmapFromView()
        viewModel.processBitmap(requireContext(), bitmap)
    }

    private fun shareImageUri(uri: Uri) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        startActivity(intent)
    }
}