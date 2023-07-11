package com.estudos.estatistica.ui.activity

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.estudos.estatistica.R
import com.estudos.estatistica.databinding.ActivityTableBinding
import com.estudos.estatistica.model.Action
import com.estudos.estatistica.model.DataForCalculations
import com.estudos.estatistica.ui.adapter.FullDataAdapter
import com.estudos.estatistica.ui.custom_view.HeaderTableLayout
import com.estudos.estatistica.ui.viewmodel.TableViewModel
import com.estudos.estatistica.util.format
import com.estudos.estatistica.util.getBitmapFromView
import com.estudos.estatistica.util.shareImage
import org.koin.android.ext.android.inject

class TableActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTableBinding

    //region Extras
    private val actionType: Action
        get() = intent.getParcelableExtra<Action>("ADADA") ?: throw java.lang.IllegalArgumentException()
    //endregion

    // region Views
    private val toolbar: Toolbar
        get() = binding.toolbar

    private val scrollView: NestedScrollView
        get() = binding.scrollView

    private val headerView: HeaderTableLayout
        get() = binding.headerTable

    private val tableView: RecyclerView
        get() = binding.recyclerTable

    private val labelAverage: AppCompatTextView
        get() = binding.labelGeneralAverage

    private val labelMedian: AppCompatTextView
        get() = binding.labelMedian

    private val labelVariance: AppCompatTextView
        get() = binding.labelVariance
    //endregion

    private val viewModel: TableViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_table, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_share){
            shareTable()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addObservers(){
        viewModel.uriPath.observe(this){
            it?.let{
                shareImage(it)
            }
        }

        viewModel.calculationData.observe(this){
            headerView.setTextClasses(getString(actionType.nameForHeaderTable))
            labelAverage.text = getString(R.string.table_average,  it.mediaGeral.format(2))
            labelMedian.text = getString(R.string.table_median, it.mediana.format(2))
            labelVariance.text = getString(R.string.table_variance, it.varianca.format(2))
            setupTable(it.dataForCalculation)
        }
    }

    private fun setupTable(calculationData: List<DataForCalculations>){
        tableView.adapter = FullDataAdapter(calculationData)
    }

    private fun shareTable(){
        val bitmap = scrollView.getBitmapFromView()
        viewModel.processBitmap(this, bitmap)
    }
}