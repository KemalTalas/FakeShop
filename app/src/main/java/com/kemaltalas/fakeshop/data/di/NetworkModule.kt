package com.kemaltalas.fakeshop.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kemaltalas.fakeshop.data.remote.ApiService
import com.kemaltalas.fakeshop.data.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideApiService() : ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder().create()

}