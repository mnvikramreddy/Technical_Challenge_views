package com.tps.challenge.features.storefeed.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tps.challenge.features.storefeed.repository.StoreRepository
import com.tps.challenge.network.ServiceResponse
import com.tps.challenge.network.model.StoreDetailsResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoreDetailsViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) :
    ViewModel() {

    private val _storeDetails = MutableLiveData<StoreDetailsResponse>()
    val storeDetails: LiveData<StoreDetailsResponse> = _storeDetails

    fun getStoreDetails(storeId: String) {
        viewModelScope.launch {
            val response = storeRepository.getStoreDetails(storeId)
            when(response){
                is ServiceResponse.Success ->{
                    _storeDetails.value = response.data
                }
                is ServiceResponse.Error -> {

                }
            }
        }
    }
}