package ru.ssau.providerrequest.data.entity

data class RequestsResponse(
    val success: Boolean,
    val message: String,
    val data: List<Data>
)