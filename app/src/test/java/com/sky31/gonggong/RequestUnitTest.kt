package com.sky31.gonggong

import com.sky31.gonggong.entity.ApiLoginResponse
import com.sky31.gonggong.entity.ApiResponse
import com.sky31.gonggong.entity.InfoData
import com.sky31.gonggong.service.DealRequestService
import com.sky31.gonggong.service.RequestService
import com.sky31.gonggong.service.ResultWrapper
import kotlinx.coroutines.test.runTest
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.test.assertNotNull

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
            is ResultWrapper.Error -> {
                error(result.message)
            }
            is ResultWrapper.NetworkError -> {
                error(result.toString())
            }
        }

        println(result)
    }

    @Test
    fun getInfoTest() = runTest {
        val jsonResponse = "{\n" +
                "  \"code\": 0,\n" +
                "  \"message\": \"OK\",\n" +
                "  \"data\": {\n" +
                "    \"student_id\": \"123456\",\n" +
                "    \"name\": \"zyh\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"birthday\": \"20001128\",\n" +
                "    \"major\": \"网络空间安全\",\n" +
                "    \"class_\": \"2\",\n" +
                "    \"entrance_day\": \"2022-09-01\",\n" +
                "    \"college\": \"计网院\"\n" +
                "  }\n" +
                "}"

        mockWebServer.enqueue(MockResponse()
            .setResponseCode(200)
            .addHeader("Content-Type", "application/json")
            .setBody(jsonResponse))

        val result: ResultWrapper<ApiResponse<InfoData>?> = dealRequestService.getInfo()

        val request: RecordedRequest = mockWebServer.takeRequest()
        assertNotNull(request.getHeader("Authorization")) {
            "Authorization header is missing"
        }

        when(result) {
            is ResultWrapper.Success -> {
                val data: InfoData = result.data!!.data

                assert(data.name == "zyh")

                println(data.toString())
            }
            is ResultWrapper.Error -> {
                error(result.message)
            }
            is ResultWrapper.NetworkError -> {
                error(result.toString())
            }
        }
    }
}