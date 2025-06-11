package com.tps.challenge.network

sealed class ServiceResponse<out T> {
    data class Success<out T>(val data: T) : ServiceResponse<T>()
    data class Error(val error: String) : ServiceResponse<Nothing>()
}