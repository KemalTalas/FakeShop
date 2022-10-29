package com.kemaltalas.fakeshop.domain.repository

import androidx.lifecycle.LiveData
import com.kemaltalas.fakeshop.data.model.Product

interface LocalDataSource {

    //Cart Functions
    suspend fun addToCart(product: Product)
    fun getCartItems() : LiveData<List<Product>>
//    suspend fun updateCartItems(product: Product)
    suspend fun deleteCartItems(product: Product)

    //Favorites Functions
    suspend fun addToFavorites(product: Product)
    fun getFavoritesItems() : LiveData<List<Product>>
    suspend fun deleteFavoritesItem(product: Product)

}