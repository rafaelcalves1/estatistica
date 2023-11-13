package com.estudos.estatistica.di

import com.estudos.estatistica.ui.features.home.model.ActionHome
import com.estudos.estatistica.ui.features.home.HomeViewModel
import com.estudos.estatistica.ui.viewmodel.SecondFragmentViewModel
import com.estudos.estatistica.ui.viewmodel.TableFragmentViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    val viewModelModule = module {
        viewModel { (action: ActionHome) ->
            SecondFragmentViewModel(action)
        }
        viewModel { TableFragmentViewModel(get()) }
        viewModel { HomeViewModel(androidApplication().resources) }
    }

}