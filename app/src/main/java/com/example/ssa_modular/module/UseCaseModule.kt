package com.example.ssa_modular.module

import com.example.domain.usecase.movie.GetLocalMoviesUseCase
import com.example.domain.usecase.movie.GetMoviesUseCase
import com.example.domain.usecase.movie.GetPagingMoviesUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule: Module = module {
    singleOf(::GetMoviesUseCase)
    singleOf(::GetPagingMoviesUseCase)
    singleOf(::GetLocalMoviesUseCase)
}
