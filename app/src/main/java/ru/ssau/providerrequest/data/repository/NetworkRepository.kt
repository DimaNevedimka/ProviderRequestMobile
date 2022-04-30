package ru.ssau.providerrequest.data.repository

import ru.ssau.providerrequest.data.service.NetworkServiceApi

class NetworkRepository(private val api: NetworkServiceApi) {

    suspend fun authenticate(login: String, password: String) = api.authenticate(login, password)

    suspend fun allRequests() = api.allRequests()

    suspend fun updateRequest(id: Int, status: String, date_time: String, comment: String, account_id: String) =
        api.updateRequest(id = id, status = status, date_time = date_time, comment = comment, account_id = account_id)
}