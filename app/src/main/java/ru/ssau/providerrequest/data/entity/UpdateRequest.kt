package ru.ssau.providerrequest.data.entity

data class UpdateRequest(
    val data: DataX,
    val message: String,
    val success: Boolean
)