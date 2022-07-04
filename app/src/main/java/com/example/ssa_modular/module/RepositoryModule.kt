package com.example.ssa_modular.module

import com.example.data.repository.search.MovieRepositoryImpl
import com.example.domain.repository.MovieRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule: Module = module {
    singleOf(::MovieRepositoryImpl) bind MovieRepository::class
}
