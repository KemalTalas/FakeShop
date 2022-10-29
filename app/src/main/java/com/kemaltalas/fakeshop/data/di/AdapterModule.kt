package com.kemaltalas.fakeshop.data.di

import com.kemaltalas.fakeshop.presentation.adapters.HomeAdapter
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



}