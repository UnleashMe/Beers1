package com.example.neverpidor.network

import android.util.Log
import com.example.neverpidor.model.beer.BeerList
import com.example.neverpidor.model.beer.BeerRequest
import com.example.neverpidor.model.snack.SnackList
import retrofit2.Call
import retrofit2.Response

class ApiClient(private val beersApiService: BeersApiService) {

    suspend fun getSnacks(): SimpleResponse<SnackList> {
        return safeApiCall { beersApiService.getSnacks() }
    }

    suspend fun getBeers(): SimpleResponse<BeerList> {
        return safeApiCall { beersApiService.getBeers() }
    }

    suspend fun addBeer(beerRequest: BeerRequest): Call<BeerRequest> = beersApiService.addBeer(beerRequest)

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