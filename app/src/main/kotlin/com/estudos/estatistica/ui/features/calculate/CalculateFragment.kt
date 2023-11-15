package com.estudos.estatistica.ui.features.calculate

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.estudos.estatistica.R
import com.estudos.estatistica.databinding.FragmentCalculateBinding
import com.estudos.estatistica.databinding.LayoutCalculateDiscreteBinding
import com.estudos.estatistica.model.CalculateType
import com.estudos.estatistica.model.actions.ActionHome
import com.estudos.estatistica.util.BaseFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CalculateFragment : BaseFragmentBinding<FragmentCalculateBinding>(
    FragmentCalculateBinding::inflate
) {

    private val args by navArgs<CalculateFragmentArgs>()


    private val viewModel: CalculateViewModel by viewModel {
        parametersOf(args.calculateType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        when (viewModel.calculateType) {
            CalculateType.DISCRETE_DATA -> setupDiscreteDataScreen()
            CalculateType.CONTINUOUS_DATA -> {}
            CalculateType.GROUPED_DISCRETE_DATA -> {}
            else -> {}
        }

    }

    // region DiscreteDataScreen
    private fun setupDiscreteDataScreen() {

        binding.toolbar.title = getString(R.string.calculate_discrete_title)

        val viewInflated = binding.calculateDiscreteLayout.inflate()

        val viewBinding = LayoutCalculateDiscreteBinding.bind(viewInflated)

        setupListenerDiscreteData(viewBinding)
    }

    private fun setupListenerDiscreteData(viewBinding: LayoutCalculateDiscreteBinding){
        viewBinding.btnCalculate.setOnClickListener {
            val values = viewBinding.editTextValues.text.toString()
            viewModel.requestCalculate(values)
        }
    }
    // endregion
}