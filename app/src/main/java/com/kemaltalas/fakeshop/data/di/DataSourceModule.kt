package com.kemaltalas.fakeshop.data.di

import com.kemaltalas.fakeshop.data.local.FakeShopDao
import com.kemaltalas.fakeshop.data.remote.ApiService
import com.kemaltalas.fakeshop.data.repo.implementation.LocalDataImp
import com.kemaltalas.fakeshop.data.repo.implementation.RemoteDataSourceImp
import com.kemaltalas.fakeshop.data.repo.repository.LocalDataSource
import com.kemaltalas.fakeshop.data.repo.repository.RemoteDataSource
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