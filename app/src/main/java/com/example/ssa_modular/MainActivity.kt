package com.example.ssa_modular

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ssa_modular.module.apiModule
import com.example.ssa_modular.module.localDataModule
import com.example.ssa_modular.module.networkModule
import com.example.ssa_modular.module.remoteDataModule
import com.example.ssa_modular.module.repositoryModule
import com.example.ssa_modular.module.useCaseModule
import com.example.ssa_modular.module.viewModelModule
import org.koin.core.context.loadKoinModules
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: IntroViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        loadKoinModules(
            listOf(
                apiModule,
                localDataModule,
                networkModule,
                remoteDataModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
            )
        )

        viewModel.setIntentData(intent.extras)
    }
}