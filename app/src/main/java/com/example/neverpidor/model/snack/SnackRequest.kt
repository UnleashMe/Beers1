package com.example.neverpidor.model.snack

data class SnackRequest(
    val description: String,
    val name: String,
    val price: Double,
    val type: String
)