package com.example.forhealth.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileApiItem(
    @SerialName(value = "user_name") val userName: String,
    @SerialName(value = "user_password") val userPassword: String = "",
    @SerialName(value = "user_height") val userHeight: String,
    @SerialName(value = "user_weight") val userWeight: String,
    @SerialName(value = "user_gender") val userGender: String,
    @SerialName(value = "user_token") val userToken: String,
    @SerialName(value = "user_id") val userId: Int,
)
