package com.example.ssa_modular

import com.example.core.BaseApplication
import com.example.core.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IntroViewModel : BaseViewModel() {

    suspend fun getPermission(): Boolean =
        withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {
            BaseApplication.getInstance().getDataStore().dataStorePermission.first()
        }

    fun setPermission() {
        CoroutineScope(Dispatchers.Main).launch {
            BaseApplication.getInstance().getDataStore().setDataStorePermission(true)
        }
    }

    fun returnData(): Boolean = false
}