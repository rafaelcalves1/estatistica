package com.estudos.estatistica.di

import com.estudos.estatistica.ui.viewmodel.SecondFragmentViewModel
import com.estudos.estatistica.ui.viewmodel.TableFragmentViewModel
import org.koin.dsl.module

object AppModule {

    val appModule = module {
        factory { SecondFragmentViewModel(get()) }
        factory { TableFragmentViewModel(get()) }
    }

}