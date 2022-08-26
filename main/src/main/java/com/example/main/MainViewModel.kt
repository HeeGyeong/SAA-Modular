package com.example.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.BaseViewModel

class MainViewModel : BaseViewModel() {

    init {
        Log.d("viewModelData", "init MainViewModel")
    }

    var checkData: String? = null

    fun initData() {
        checkData = "0"
        index = 1
    }

    private var index = 1
    fun addData() {
        checkData += "$index"
        index++
    }
}