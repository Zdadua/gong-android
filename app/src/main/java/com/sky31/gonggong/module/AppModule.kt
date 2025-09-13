package com.sky31.gonggong.module

import android.content.Context
import androidx.room.Room
import com.sky31.gonggong.database.AppDatabase
import com.sky31.gonggong.manager.AppConfigManager
import com.sky31.gonggong.service.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * 提供AppRepository单例
     */
    @Provides
    @Singleton
    fun provideAppRepository(db: AppDatabase): AppRepository {
        return AppRepository(db)
    }

    @Provides
    @Singleton
    fun provideConfigManager(db: AppDatabase): AppConfigManager {
        val configDao = db.getConfigDao()

        return AppConfigManager(configDao)
    }
}