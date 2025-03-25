package com.sky31.gonggong

import com.sky31.gonggong.entity.ApiLoginResponse
import com.sky31.gonggong.service.DealRequestService
import com.sky31.gonggong.service.RequestService
import com.sky31.gonggong.service.ResultWrapper
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RequestUnitTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var dealRequestService: DealRequestService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val authInterceptor = Interceptor { chain ->
            val originalRequest: Request = chain.request()

            if(originalRequest.method == "GET") {
                val token = ""

                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()

                chain.proceed(newRequest)
            } else {
                chain.proceed(originalRequest)
            }
        }

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dealRequestService = DealRequestService(retrofit.create(RequestService::class.java))
    }

    @After
    fun testDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun loginTest() = runTest {

        val jsonResponse = "{\"access_token\": \"114514\", \"expires_in\": \"9999\", \"token_type\": \"Bearer\"}"
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(200)
            .addHeader("Content-Type", "application/json")
            .setBody(jsonResponse))

        val result: ResultWrapper<ApiLoginResponse?> = dealRequestService.login("202105650301", "13586591252Xx")

        when(result) {
            is ResultWrapper.Success -> {
                val data = result.data

                assert(data?.accessToken == "114514")
                assert(data?.tokenType == "Bearer")
                assert(data?.expiresIn == 9999)
            }
            is ResultWrapper.Error -> TODO()
            is ResultWrapper.NetworkError -> TODO()
        }

        println(result)
    }
}