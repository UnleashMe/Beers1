package com.example.neverpidor.network

import com.example.neverpidor.model.SnackList
import retrofit2.Response
import retrofit2.http.GET

interface BeersApiService {

    @GET("snacks")
    suspend fun getSnacks(): Response<SnackList>
}