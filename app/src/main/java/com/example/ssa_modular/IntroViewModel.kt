package com.example.ssa_modular

import android.os.Bundle
import com.example.core.BaseApplication
import com.example.core.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * MultiModule 구조에서 introViewModel을 전역으로 사용할 수 없음.
 *
 * App Module에 대한 참조를 하위 모듈에서 할 수 없기 때문.
 */
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

    var insertData: Bundle? = null

    fun setIntentData(data: Bundle?) {
        insertData = data
    }

    fun getIntentData(): Bundle? = insertData
}