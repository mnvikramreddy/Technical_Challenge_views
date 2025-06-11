package com.tps.challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : ViewModel() {

    private val _storeId = MutableLiveData<String>()
    val storeId: LiveData<String> = _storeId

    fun setStoreId(id: String) {
        _storeId.value = id
    }
}