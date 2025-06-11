package com.tps.challenge.features.storefeed.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tps.challenge.features.storefeed.repository.StoreRepository
import com.tps.challenge.network.ServiceResponse
import com.tps.challenge.network.model.StoreResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoreFeedViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : ViewModel() {

    private val _query = MutableLiveData<String>("")

    private val _storeList = MutableLiveData<List<StoreResponse>>()
   // val storeList: LiveData<List<StoreResponse>> = _storeList

    val storeList: LiveData<List<StoreResponse>> = MediatorLiveData<List<StoreResponse>>().apply {
        addSource(_storeList) {
          value =  filterList(it, _query.value.toString())
        }
       addSource(_query) { query->
          value = filterList(_storeList.value, query)
       }
   }

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _storeFeedUiState = MutableLiveData(StoreFeedUiState())
    val storeFeedUiState: LiveData<StoreFeedUiState> = _storeFeedUiState

    //private val storeList: List<StoreResponse> = emptyList()
    init {
        getStoreFeed()
    }

    fun getStoreFeed() {
        viewModelScope.launch {
            val result = storeRepository.getStoreFeed()
            when (result) {
                is ServiceResponse.Success -> {
                    result.data.let {
                        _storeList.value = it.distinctBy { it.id }
                        _storeFeedUiState.value?.copy(
                            storeList = it,
                            isLoading = false
                        )
                    }
                }

                is ServiceResponse.Error -> {
                    _error.value = result.error
                    _storeFeedUiState.value?.copy(
                        error = result.error,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun filterList(storeList: List<StoreResponse>?, query: String): List<StoreResponse> {
        return storeList.orEmpty().filter {
                it.name.contains(query, ignoreCase = true) or it.description.contains(query, ignoreCase = true)
            }
    }

    fun deleteItemAt(position: Int): StoreResponse? {
        val mutableList = _storeList.value?.toMutableList() ?: return null
        return if (position in mutableList.indices) {
            val removed = mutableList.removeAt(position)
            _storeList.value = mutableList
            removed
        } else null
    }

    fun restoreItemAt(item: StoreResponse, position: Int) {
        val mutableList = _storeList.value?.toMutableList() ?: return
        mutableList.add(position, item)
        _storeList.value = mutableList
    }

    fun moveItem(from: Int, to: Int) {
        val mutableList = _storeList.value?.toMutableList() ?: return
        val item = mutableList.removeAt(from)
        mutableList.add(to, item)
        _storeList.value = mutableList
    }

    fun onSearchQueryChanged(query: kotlin.String) {
        _query.value = query
    }
}

data class StoreFeedUiState(
    val storeList: List<StoreResponse> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = true
)