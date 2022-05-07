package ru.ssau.providerrequest.data.service

import retrofit2.http.GET
import ru.ssau.providerrequest.data.entity.AuthenticationRequest
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ssau.providerrequest.data.entity.RequestsResponse
import ru.ssau.providerrequest.data.entity.UpdateRequest

interface NetworkServiceApi {

    @POST("/public/api/auth")
    suspend fun authenticate(
        @Query(LOGIN_RESPONSE) login: String,
        @Query(PASSWORD_RESPONSE) password: String
    ): AuthenticationRequest

    @GET("public/api/request")
    suspend fun allRequests(): RequestsResponse

    @PUT("public/api/request/{id}")
    suspend fun updateRequest(
        @Path("id") id: Int,
        @Query("status") status: String,
        @Query("date_time") date_time: String,
        @Query("comment") comment: String,
        @Query("account_id") account_id: String,
    ): UpdateRequest

    companion object {
        private const val LOGIN_RESPONSE = "login"
        private const val PASSWORD_RESPONSE = "password"
    }
}