package com.example.core

import android.app.Application
import com.example.core.util.DataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application() {

    private lateinit var dataStore: DataStore

    // getDataStore를 사용하기 위한 instance
    companion object {
        private lateinit var baseApplication: BaseApplication
        fun getInstance(): BaseApplication = baseApplication
    }

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
        dataStore = DataStore(this)

        // Koin setting
        startKoin {
            androidContext(this@BaseApplication)
        }
    }

    fun getDataStore(): DataStore = dataStore
}
