package com.sky31.gonggong.service

import com.sky31.gonggong.entity.ApiLoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RequestService {
    @POST("login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Call<ApiLoginResponse>
}