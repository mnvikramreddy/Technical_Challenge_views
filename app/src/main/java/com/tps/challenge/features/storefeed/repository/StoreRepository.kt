package com.tps.challenge.features.storefeed.repository

import com.tps.challenge.Constants
import com.tps.challenge.network.ServiceResponse
import com.tps.challenge.network.TPSCoroutineService
import com.tps.challenge.network.model.StoreDetailsResponse
import com.tps.challenge.network.model.StoreResponse
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val tpsCoroutineService: TPSCoroutineService
) {

    suspend fun getStoreFeed(): ServiceResponse<List<StoreResponse>> =

        try {
            ServiceResponse.Success(
                tpsCoroutineService.getStoreFeed(
                    Constants.DEFAULT_LATITUDE,
                    Constants.DEFAULT_LONGITUDE
                )
            )
        } catch (e: Exception) {
            ServiceResponse.Error(error = e.toString())
        }


    suspend fun getStoreDetails(storeId: String): ServiceResponse<StoreDetailsResponse> =
        try {
            ServiceResponse.Success(
                tpsCoroutineService.getStoreDetails(storeId)
            )
        } catch (e: Exception) {
            ServiceResponse.Error(e.toString())
        }
}