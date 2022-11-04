package com.kemaltalas.fakeshop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kemaltalas.fakeshop.data.model.CartItems
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.model.UserDetails
import com.kemaltalas.fakeshop.data.util.Converters

@Database(entities = [Product::class,CartItems::class,UserDetails::class], version = 7, exportSchema = true)
@TypeConverters(Converters::class)
abstract class FakeShopDatabase : RoomDatabase() {
    abstract fun fakeShopDao() : FakeShopDao
}