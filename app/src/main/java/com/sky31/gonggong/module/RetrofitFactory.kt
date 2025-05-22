package com.sky31.gonggong.module

import com.sky31.gonggong.GlobalConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    fun create(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GlobalConfig.HOST_CONFIG)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createWithClient(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GlobalConfig.HOST_CONFIG)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}