package ru.ssau.providerrequest.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Client(
    val birthday: String,
    val created_at: String,
    val id: Int,
    val name: String,
    val passport: String,
    val patronymic: String,
    val surname: String,
    val updated_at: String
) : Parcelable