package com.kemaltalas.fakeshop.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kemaltalas.fakeshop.data.model.CartItems
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.model.UserDetails

@Dao
interface FakeShopDao  {

    //Favorites
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorites(product: Product)

    @Query("select * from favorites")
    fun favoriteItems() : LiveData<List<Product>>

    @Delete
    suspend fun deleteFavorites(product: Product)

   @Query("delete from favorites")
    suspend fun clearAllFavorites()

    @Update
    suspend fun updateFavorites(product: Product)

    //CART
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToCart(cartItems: CartItems)

    @Query("select * from cartitems")
    fun cartItems() : LiveData<List<CartItems>>

    @Delete
    suspend fun deleteCartItems(cartItems: CartItems)

    @Query("delete from cartitems")
    suspend fun clearAllCart()

    //User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(userDetails: UserDetails)

    @Query("select * from userdetail")
    fun getUserDetail() : LiveData<UserDetails>

    @Update
    suspend fun updateUser(userDetails: UserDetails)

    @Query("delete from userdetail")
    suspend fun deleteAllUser()





}