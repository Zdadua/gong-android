package com.sky31.gonggong

import android.app.Application
import androidx.room.Room
import com.sky31.gonggong.database.AppDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainApplication: Application() {
    companion object {
        lateinit var appDatabase: AppDatabase
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.NAME
        ).build()

        retrofit = Retrofit.Builder()
        .baseUrl(GlobalConfig.hostConfig)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}