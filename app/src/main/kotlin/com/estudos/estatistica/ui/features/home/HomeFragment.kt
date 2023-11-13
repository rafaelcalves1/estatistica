package com.estudos.estatistica.ui.features.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.estudos.estatistica.databinding.FragmentHomeBinding
import com.estudos.estatistica.ui.features.home.model.Action
import com.estudos.estatistica.ui.features.home.model.ActionHome
import com.estudos.estatistica.util.BaseFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragmentBinding<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewActions.apply {
            adapter = HomeAdapter().apply {
                submitList(viewModel.listOfActions)
                setOnClickListener(::setOnClickListenerAdapter)
            }
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setOnClickListenerAdapter(action: Action) {
        when (val validAction = action.action) {
            ActionHome.HISTORIC -> {
                findNavController().navigate(HomeFragmentDirections.toHistoric())
            }
            else -> {
                findNavController().navigate(HomeFragmentDirections.toCalculate(calculateType = validAction.value))
            }
        }
    }


}