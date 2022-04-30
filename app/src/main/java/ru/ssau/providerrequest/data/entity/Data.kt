package ru.ssau.providerrequest.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val client: Client,
    val comment: String,
    val date_time: String,
    val home: Home,
    val id: Int,
    val status: String,
    val tariff: Tariff,
    val account_id: String
) : Parcelable