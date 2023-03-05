package com.example.neverpidor.data

import com.example.neverpidor.model.beer.BeerList
import com.example.neverpidor.model.beer.BeerRequest
import com.example.neverpidor.model.snack.SnackList
import com.example.neverpidor.model.snack.SnackRequest
import com.example.neverpidor.network.NetworkLayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

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
    suspend fun getBeers(): Flow<BeerList>? {

        val request = NetworkLayer.apiClient.getBeers()

        if (request.failed) {
            return null
        }
        if (!request.isSuccessful) {
            return null
        }
        return flowOf( request.body)
    }
    suspend fun addBeer(beerRequest: BeerRequest) = NetworkLayer.apiClient.addBeer(beerRequest)

    suspend fun addSnack(snackRequest: SnackRequest) = NetworkLayer.apiClient.addSnack(snackRequest)

    suspend fun deleteBeer(beerId: String) = NetworkLayer.apiClient.deleteBeer(beerId)

    suspend fun deleteSnack(snackId: String) = NetworkLayer.apiClient.deleteSnack(snackId)
}