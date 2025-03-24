package com.sky31.gonggong.instance

import com.sky31.gonggong.service.RequestService

object RequestServiceInstance {
    private val requestService: RequestService by lazy {
        RetrofitClient.retrofit.create(RequestService::class.java)
    }

    fun getInstance(): RequestService {
        return requestService
    }
}