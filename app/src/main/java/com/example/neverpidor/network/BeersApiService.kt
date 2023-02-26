package com.example.neverpidor.network

import com.example.neverpidor.model.beer.BeerList
import com.example.neverpidor.model.beer.BeerRequest
import com.example.neverpidor.model.snack.SnackList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BeersApiService {

    @GET("snacks")
    suspend fun getSnacks(): Response<SnackList>

    @GET("beverages")
    suspend fun getBeers(): Response<BeerList>

    @POST("beverages/add-beverage")
    fun addBeer(@Body beerRequest: BeerRequest): Call<BeerRequest>
}