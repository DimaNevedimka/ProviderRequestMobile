package ru.ssau.providerrequest.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tariff(
    val archive: String,
    val created_at: String,
    val desc: String,
    val id: Int,
    val name: String,
    val updated_at: String
) : Parcelable