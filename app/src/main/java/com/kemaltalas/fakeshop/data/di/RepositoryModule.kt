package com.kemaltalas.fakeshop.data.di

import com.kemaltalas.fakeshop.data.repo.implementation.FakeShopRepositoryImp
import com.kemaltalas.fakeshop.data.repo.repository.FakeShopRepository
import com.kemaltalas.fakeshop.data.repo.repository.LocalDataSource
import com.kemaltalas.fakeshop.data.repo.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesRepository(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource) : FakeShopRepository {
        return FakeShopRepositoryImp(remoteDataSource,localDataSource)
    }

}