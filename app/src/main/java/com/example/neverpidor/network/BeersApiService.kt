package com.example.neverpidor.network

import com.example.neverpidor.model.beer.BeerList
import com.example.neverpidor.model.beer.BeerPostResponse
import com.example.neverpidor.model.beer.BeerRequest
import com.example.neverpidor.model.snack.SnackDeleteResponse
import com.example.neverpidor.model.snack.SnackList
import com.example.neverpidor.model.snack.SnackRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BeersApiService {

    @GET("snacks")
    suspend fun getSnacks(): Response<SnackList>

    @GET("beverages")
    suspend fun getBeers(): Response<BeerList>

    @POST("beverages/add-beverage")
    suspend fun addBeer(@Body beerRequest: BeerRequest): Response<BeerPostResponse>

    @POST("snacks/add-snack")
    suspend fun addSnack(@Body snackRequest: SnackRequest): Response<SnackDeleteResponse>

    @DELETE("beverages/{beerId}")
    suspend fun deleteBeer(@Path ("beerId") beerId: String): Response<BeerPostResponse>

    @DELETE("snacks/{snackId}")
    suspend fun deleteSnack(@Path ("snackId") snackId: String): Response<SnackDeleteResponse>
}