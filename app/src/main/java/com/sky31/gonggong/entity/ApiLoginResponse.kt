package com.sky31.gonggong.entity

import com.google.gson.annotations.SerializedName

data class ApiLoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Int
)