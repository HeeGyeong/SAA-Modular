package com.example.ssa_modular.module

import androidx.room.Room
import com.example.data.db.movie.MovieDao
import com.example.data.db.movie.MovieDatabase
import com.example.data.repository.search.local.MovieLocalDataSource
import com.example.data.repository.search.local.MovieLocalDataSourceImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val localDataModule: Module = module {
    singleOf(::MovieLocalDataSourceImpl) bind MovieLocalDataSource::class

    single<MovieDao> { get<MovieDatabase>().movieDao() }
    single<MovieDatabase> {
        Room.databaseBuilder(
            get(),
            MovieDatabase::class.java,
            "Movie.db" // Local DB name
        ).build()
    }
}
