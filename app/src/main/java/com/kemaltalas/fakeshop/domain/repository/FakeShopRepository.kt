package com.kemaltalas.fakeshop.domain.repository

import androidx.lifecycle.LiveData
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.util.Resource
import retrofit2.Response

interface FakeShopRepository {

    //Product Remote Functions
    suspend fun getAllProducts() : Resource<ArrayList<Product>>
    suspend fun getProduct(itemId: Int) : Resource<Product>
    suspend fun getAllCategories(): Resource<ArrayList<String>>
    suspend fun getCategoryProducts(category: String) : Resource<Product>

    //Local Data Cart Functions
    suspend fun addToCart(product: Product)
    fun getCartItems() : LiveData<List<Product>>
//    suspend fun updateCartItems(product: Product)
    suspend fun deleteCartItems(product: Product)

    //Local Data Favorites Functions
    suspend fun addToFavorites(product: Product)
    suspend fun deleteFavorites(product: Product)
    fun getFavoriteItems() : LiveData<List<Product>>

}