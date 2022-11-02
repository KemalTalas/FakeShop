package com.kemaltalas.fakeshop.domain.repository

import androidx.lifecycle.LiveData
import com.kemaltalas.fakeshop.data.model.CartItems
import com.kemaltalas.fakeshop.data.model.Product

interface LocalDataSource {

    //Cart Functions
    suspend fun addToCart(cartItems: CartItems)
    fun getCartItems() : LiveData<List<CartItems>>
    suspend fun deleteCartItems(cartItems: CartItems)
    suspend fun clearAllCart()

    //Favorites Functions
    suspend fun addToFavorites(product: Product)
    fun getFavoritesItems() : LiveData<List<Product>>
    suspend fun deleteFavoritesItem(product: Product)
    suspend fun clearAllFavorites()

}