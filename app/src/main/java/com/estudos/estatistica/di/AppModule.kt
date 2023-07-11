package com.estudos.estatistica.di

import com.estudos.estatistica.model.ActionHome
import com.estudos.estatistica.ui.home.HomeViewModel
import com.estudos.estatistica.ui.viewmodel.SecondFragmentViewModel
import com.estudos.estatistica.ui.viewmodel.TableViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    val viewModelModule = module {
        viewModel { (action: ActionHome) ->
            SecondFragmentViewModel(action)
        }
        viewModel { TableViewModel(get()) }
        viewModel { HomeViewModel(androidApplication().resources) }
    }

}