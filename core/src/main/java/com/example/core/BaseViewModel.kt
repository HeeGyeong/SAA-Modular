package com.example.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun showProgress() {
        _isLoading.value = true
    }

    fun hideProgress() {
        _isLoading.value = false
    }

    /**
     * DataStore 사용을 위한 get
     *
     * await() 를 사용하기 위하여 suspend 로 사용.
     * 동기화 된 상태로 해당 값을 가져오는 것이 아닌 비동기로 값을 사용한다면 await 를 사용하지 않아도 된다.
     */
    suspend fun getDataStore(): String = CoroutineScope(Dispatchers.Main).async {
        BaseApplication.getInstance().getDataStore().dataStoreText.first()
    }.await()

    /**
     * DataStore 사용을 위한 set
     */
    fun setDataStore(text: String) {
        CoroutineScope(Dispatchers.Main).launch {
            BaseApplication.getInstance().getDataStore().setDataStoreText(text)
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}