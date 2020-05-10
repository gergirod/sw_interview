package ger.girod.interview.ui.utils

import ger.girod.interview.ui.detail.DetailViewModel
import ger.girod.interview.ui.main.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MainViewModel(get(), get()) }
    factory { DetailViewModel(get())}
}