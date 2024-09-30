package com.example.reading.data.di

import com.example.reading.data.AppService
import com.example.reading.data.RepositoryImpl
import com.example.reading.data.locadatasource.AppStorageLocalDataSource
import com.example.reading.data.locadatasource.StoryLocalDataSource
import com.example.reading.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return httpLoggingInterceptor
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.9.135:8000/")
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideAppService(retrofit: Retrofit): AppService {
        return retrofit.create(AppService::class.java)
    }

    @Provides
    fun provideRepository(
        appService: AppService,
        localDataSource: StoryLocalDataSource,
        appStorageLocalDataSource: AppStorageLocalDataSource
    ): Repository {
        return RepositoryImpl(appService, localDataSource, appStorageLocalDataSource)
    }
}