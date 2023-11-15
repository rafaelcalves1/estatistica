package com.estudos.estatistica.ui.features.calculate

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.estudos.estatistica.R
import com.estudos.estatistica.databinding.FragmentCalculateBinding
import com.estudos.estatistica.databinding.LayoutCalculateDiscreteBinding
import com.estudos.estatistica.databinding.LayoutCalculateGroupedDiscreteDataBinding
import com.estudos.estatistica.model.CalculateType
import com.estudos.estatistica.util.BaseFragmentBinding
import com.estudos.estatistica.util.hideKeyboard
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
            CalculateType.GROUPED_DISCRETE_DATA -> setupGroupedDiscreteDataScreen()
            else -> {}
        }

        setupObservers()
    }

    // region DiscreteDataScreen
    private fun setupDiscreteDataScreen() {

        binding.toolbar.title = getString(R.string.calculate_discrete_title)

        val viewInflated = binding.calculateDiscreteLayout.inflate()

        val viewBinding = LayoutCalculateDiscreteBinding.bind(viewInflated)

        setupListenerDiscreteData(viewBinding)
    }

    private fun setupListenerDiscreteData(viewBinding: LayoutCalculateDiscreteBinding) {
        viewBinding.btnCalculate.setOnClickListener {
            val values = viewBinding.editTextValues.text.toString()
            viewModel.requestCalculate(values)
        }
    }
    // endregion

    // region GroupedDiscreteDataScreen
    private fun setupGroupedDiscreteDataScreen() {

        binding.toolbar.title = getString(R.string.calculate_grouped_discrete_title)
        binding.imgPersona.visibility = View.GONE

        val viewInflated = binding.calculateDiscreteUngroupedLayout.inflate()

        val viewBinding = LayoutCalculateGroupedDiscreteDataBinding.bind(viewInflated)

        setupListenerGroupedDiscreteData(viewBinding)
        setupObservers(viewBinding)

        viewBinding.tableView.build(
            numberOfColumns = viewModel.numberOfRows
        )
    }

    private fun setupListenerGroupedDiscreteData(viewBinding: LayoutCalculateGroupedDiscreteDataBinding) {
        viewBinding.btnCalculate.setOnClickListener {

        }

        viewBinding.btnAdd.setOnClickListener {
            viewModel.addValueToList(
                viewBinding.editTextClass.text.toString(),
                viewBinding.editTextFrequency.text.toString()
            )

            viewBinding.editTextClass.text = null
            viewBinding.editTextFrequency.text = null
            clearFocus(viewBinding)
        }
    }

    private fun setupObservers(viewBinding: LayoutCalculateGroupedDiscreteDataBinding) {
        viewModel.currentList.observe(viewLifecycleOwner) {
            viewBinding.tableView.submitList(it.toList())
        }

        viewModel.currentNumberOfValues.observe(viewLifecycleOwner) {
            viewBinding.textViewInfoValueQuantity.text =
                getString(R.string.calculate_grouped_discrete_data_info_quantity, it)
        }
    }

    private fun clearFocus(viewBinding: LayoutCalculateGroupedDiscreteDataBinding) {
        viewBinding.editTextClass.clearFocus()
        viewBinding.editTextFrequency.clearFocus()

        activity?.hideKeyboard(viewBinding.editTextClass, viewBinding.editTextFrequency)
    }
    // endregion

    private fun setupObservers() {
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(
                context,
                "Ocorreu um erro! Verifique os dados e tente novamente",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}