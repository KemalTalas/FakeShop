package com.kemaltalas.fakeshop.data.di

import com.kemaltalas.fakeshop.data.local.FakeShopDao
import com.kemaltalas.fakeshop.data.remote.ApiService
import com.kemaltalas.fakeshop.domain.implementation.LocalDataImp
import com.kemaltalas.fakeshop.domain.implementation.RemoteDataSourceImp
import com.kemaltalas.fakeshop.domain.repository.LocalDataSource
import com.kemaltalas.fakeshop.domain.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun providesLocalDataSource(dao: FakeShopDao) : LocalDataSource {
        return LocalDataImp(dao)
    }

    @Singleton
    @Provides
    fun provideShopRemoteDataSource(apiService: ApiService) : RemoteDataSource {
        return RemoteDataSourceImp(apiService)
    }

}