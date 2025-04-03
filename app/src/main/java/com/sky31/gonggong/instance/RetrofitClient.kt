package com.sky31.gonggong.instance

import com.sky31.gonggong.GlobalConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(GlobalConfig.hostConfig)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}