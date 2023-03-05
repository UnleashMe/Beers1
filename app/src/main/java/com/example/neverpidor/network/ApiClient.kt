package com.example.neverpidor.network

import android.util.Log
import com.example.neverpidor.model.beer.BeerList
import com.example.neverpidor.model.beer.BeerPostResponse
import com.example.neverpidor.model.beer.BeerRequest
import com.example.neverpidor.model.snack.SnackDeleteResponse
import com.example.neverpidor.model.snack.SnackList
import com.example.neverpidor.model.snack.SnackRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Call
import retrofit2.Response

class ApiClient(private val beersApiService: BeersApiService) {

    suspend fun getSnacks(): SimpleResponse<SnackList> {
        return safeApiCall { beersApiService.getSnacks() }
    }

     suspend fun getBeers(): SimpleResponse<BeerList> {
        return safeApiCall { beersApiService.getBeers() }
    }

    suspend fun addBeer(beerRequest: BeerRequest): Response<BeerPostResponse> = beersApiService.addBeer(beerRequest)

    suspend fun addSnack(snackRequest: SnackRequest): Response<SnackDeleteResponse> = beersApiService.addSnack(snackRequest)

    suspend fun deleteBeer(beerId: String): Response<BeerPostResponse> = beersApiService.deleteBeer(beerId)

    suspend fun deleteSnack(snackId: String): Response<SnackDeleteResponse> = beersApiService.deleteSnack(snackId)

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