package com.kemaltalas.fakeshop.data.di

import com.kemaltalas.fakeshop.presentation.adapters.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun providesHomeAdapter() : HomeAdapter {
        return HomeAdapter()
    }

    @Singleton
    @Provides
    fun providesCategoriesAdapter() : CategoriesAdapter{
        return CategoriesAdapter()
    }

    @Singleton
    @Provides
    fun providesProductsAdapter() : ProductsAdapter{
        return ProductsAdapter()
    }

    @Singleton
    @Provides
    fun providesFavoritesAdapter() : FavoritesAdapter{
        return FavoritesAdapter()
    }

    @Singleton
    @Provides
    fun providesBasketAdapter() : BasketAdapter{
        return BasketAdapter()
    }


}