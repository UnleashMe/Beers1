package com.example.neverpidor.model.beer

data class BeerRequest(
    val alcPercentage: Double,
    val description: String,
    val name: String,
    val price: Double,
    val type: String,
    val volume: Double
)