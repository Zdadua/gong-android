package com.sky31.gonggong

import com.sky31.gonggong.entity.ApiLoginResponse
import com.sky31.gonggong.service.RequestService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RequestUnitTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var retrofit: Retrofit

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @After
    fun testDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun loginTest() {
        
        val apiService = retrofit.create(RequestService::class.java)

        val jsonResponse = "{\"access_token\": \"114514\", \"expires_in\": \"9999\", \"token_type\": \"Bearer\"}"
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(200)
            .addHeader("Content-Type", "application/json")
            .setBody(jsonResponse))

        val call: Call<ApiLoginResponse> = (apiService as RequestService).login("202105650301", "13586591252Xx")
        val response: Response<ApiLoginResponse> = call.execute()

        if(response.isSuccessful) {
            val apiLoginResponse: ApiLoginResponse? = response.body()
            if (apiLoginResponse != null) {
                val accessToken = apiLoginResponse.accessToken
                val expiresIn = apiLoginResponse.expiresIn
                val tokenType = apiLoginResponse.tokenType

                assert(accessToken == "114514")
                assert(expiresIn == 9999)
                assert(tokenType == "Bearer")

            } else {
                assert(false)
            }
        }
    }
}