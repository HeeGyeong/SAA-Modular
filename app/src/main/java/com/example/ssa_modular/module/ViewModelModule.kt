package com.example.ssa_modular.module

import com.example.search.SearchViewModel
import com.example.ssa_modular.IntroViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val viewModelModule: Module = module {

    singleOf(::IntroViewModel)
    singleOf(::SearchViewModel)
}
