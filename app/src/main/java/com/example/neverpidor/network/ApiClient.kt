package com.example.neverpidor.network

import android.util.Log
import android.widget.Toast
import com.example.neverpidor.model.Snack
import com.example.neverpidor.model.SnackList
import retrofit2.Response

class ApiClient(private val beersApiService: BeersApiService) {

    suspend fun getSnacks(): SimpleResponse<SnackList> {
        return safeApiCall { beersApiService.getSnacks() }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            Log.i("ApiClient", "Success")
            SimpleResponse.success(apiCall.invoke())

        } catch (e: Exception) {
            Log.i("ApiClient", "Failure")
            SimpleResponse.failure(e)
        }
    }
}