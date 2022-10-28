package com.kemaltalas.fakeshop.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kemaltalas.fakeshop.data.model.Product

@Dao
interface FakeShopDao  {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorites(product: Product)

    @Query("select * from favorites")
    fun favoriteItems() : LiveData<List<Product>>

    @Delete
    suspend fun deleteFavorites(product: Product)

//    @Query("delete from favorites")
//    suspend fun clearAllFavorites()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToCart(product: Product)

    @Query("select * from favorites")
    fun cartItems() : LiveData<List<Product>>

    @Delete
    suspend fun deleteCartItems(product: Product)





}