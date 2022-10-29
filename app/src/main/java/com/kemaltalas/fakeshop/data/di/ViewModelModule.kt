package com.kemaltalas.fakeshop.data.di

import android.app.Application
import com.kemaltalas.fakeshop.domain.usecase.ProductUseCase
import com.kemaltalas.fakeshop.presentation.viewmodels.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {


    @Singleton
    @Provides
    fun providesHomeViewModel(app : Application, productUseCase: ProductUseCase) : HomeViewModel {
        return HomeViewModel(app, productUseCase)
    }

}