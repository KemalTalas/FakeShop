package com.kemaltalas.fakeshop.data.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.kemaltalas.fakeshop.data.local.FakeShopDao
import com.kemaltalas.fakeshop.data.local.FakeShopDatabase
import com.kemaltalas.fakeshop.data.util.Converters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Singleton
    @Provides
    fun providesDatabase(app : Application, gson: Gson) : FakeShopDatabase{
        return Room.databaseBuilder(app,FakeShopDatabase::class.java,"FakeShopDB")
            .addTypeConverter(Converters(gson))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesDao(database: FakeShopDatabase) : FakeShopDao{
        return database.fakeShopDao()
    }

}