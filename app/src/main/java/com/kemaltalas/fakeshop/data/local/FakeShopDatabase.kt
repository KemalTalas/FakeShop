package com.kemaltalas.fakeshop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kemaltalas.fakeshop.data.model.Product

@Database(entities = [Product::class], version = 1)
abstract class FakeShopDatabase : RoomDatabase() {
    abstract fun fakeShopDao() : FakeShopDao
}