package com.example.neverpidor.data

import com.example.neverpidor.model.Snack
import com.example.neverpidor.model.SnackList
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
        /*return listOf(
            Snack("0", "", "Забеись чипасы", "Чипсы", 10.5, "Закусь", ""),
            Snack("1", "", "Так себе сухарики", "сухарики", 153.2, "Закусь", "")
            )*/
    }
}