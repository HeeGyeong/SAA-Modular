package com.example.ssa_modular.module

import com.example.core.util.NetworkManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule: Module = module {
    singleOf(::NetworkManager)
}
