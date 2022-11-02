package com.kemaltalas.fakeshop.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kemaltalas.fakeshop.data.local.FakeShopDao
import com.kemaltalas.fakeshop.data.local.FakeShopDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Singleton
    @Provides
    fun providesDatabase(app : Application) : FakeShopDatabase{
        return Room.databaseBuilder(app,FakeShopDatabase::class.java,"FakeShopDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesDao(database: FakeShopDatabase) : FakeShopDao{
        return database.fakeShopDao()
    }

}