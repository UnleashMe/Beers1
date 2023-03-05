package com.example.neverpidor.model.snack

data class SnackDeleteResponse(
    val deletedSnack: DeletedSnack = DeletedSnack(),
    val msg: String = ""
)