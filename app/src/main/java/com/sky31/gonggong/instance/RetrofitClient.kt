package com.sky31.gonggong.instance

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("leo.gong.online/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}