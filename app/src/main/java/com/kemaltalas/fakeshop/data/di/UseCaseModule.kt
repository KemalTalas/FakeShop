package com.kemaltalas.fakeshop.data.di

import com.kemaltalas.fakeshop.domain.repository.FakeShopRepository
import com.kemaltalas.fakeshop.domain.usecase.FavoritesUseCase
import com.kemaltalas.fakeshop.domain.usecase.ProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun providesProductUseCase(repository: FakeShopRepository) : ProductUseCase{
        return ProductUseCase(repository)
    }
    @Singleton
    @Provides
    fun providesFavoritesUseCase(repository: FakeShopRepository) : FavoritesUseCase{
        return FavoritesUseCase(repository)
    }

}