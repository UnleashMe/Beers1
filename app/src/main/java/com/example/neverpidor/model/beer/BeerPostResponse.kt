package com.example.neverpidor.model.beer

// post response duh
data class BeerPostResponse(
    val createdBeverage: Data = Data(),
    val msg: String = ""
)