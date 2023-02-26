package com.example.neverpidor.data

import com.example.neverpidor.model.beer.BeerList
import com.example.neverpidor.model.beer.BeerRequest
import com.example.neverpidor.model.snack.SnackList
import com.example.neverpidor.network.NetworkLayer

class MenuItemsRepository {

    suspend fun getSnacks(): SnackList? {

        val request = NetworkLayer.apiClient.getSnacks()

       if (request.failed) {
            return null
        }
        if (!request.isSuccessful) {
            return null
        }
        return request.body
    }
    suspend fun getBeers(): BeerList? {

        val request = NetworkLayer.apiClient.getBeers()

        if (request.failed) {
            return null
        }
        if (!request.isSuccessful) {
            return null
        }
        return request.body
    }
    suspend fun addBeer(beerRequest: BeerRequest) = NetworkLayer.apiClient.addBeer(beerRequest)
}