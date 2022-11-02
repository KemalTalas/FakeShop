package com.kemaltalas.fakeshop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kemaltalas.fakeshop.data.model.CartItems
import com.kemaltalas.fakeshop.data.model.Product

@Database(entities = [Product::class,CartItems::class], version = 2, exportSchema = true)
abstract class FakeShopDatabase : RoomDatabase() {
    abstract fun fakeShopDao() : FakeShopDao
}