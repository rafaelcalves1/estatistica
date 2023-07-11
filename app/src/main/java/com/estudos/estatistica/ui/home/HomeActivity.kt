package com.estudos.estatistica.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.estudos.estatistica.databinding.HomeActivityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding

    private val viewModel: HomeViewModel by viewModel()
    private val adapter = HomeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        configureListeners()
    }

    private fun setupRecyclerView() {
        binding.btnsActions.apply {
            adapter = this@HomeActivity.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        adapter.submitList(viewModel._listBtnActions)
    }

    private fun configureListeners() {
        adapter.setOnClickListener {
            /*
            navController.navigate(
                HomeFragmentDirections.actionFirstFragmentToSecondFragment(
                    it.action
                )
            )*/
        }
    }
}