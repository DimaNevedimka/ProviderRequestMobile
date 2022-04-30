package ru.ssau.providerrequest.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Home(
    val address: String,
    val comment: String,
    val coords: String,
    val created_at: String,
    val email_manager: String,
    val id: Int,
    val phone_manager: String,
    val updated_at: String
) : Parcelable