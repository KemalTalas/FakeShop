package com.kemaltalas.fakeshop.data.repo.repository

import androidx.lifecycle.LiveData
import com.kemaltalas.fakeshop.data.model.*
import com.kemaltalas.fakeshop.data.util.Resource
import retrofit2.Response

interface FakeShopRepository {

    //Product Remote Functions
    suspend fun getAllProducts() : Resource<ArrayList<Product>>
    suspend fun getProduct(itemId: Int) : Resource<Product>
    suspend fun getAllCategories(): Resource<ArrayList<String>>
    suspend fun getCategoryProducts(category: String) : Resource<ArrayList<Product>>

    //Local Data Cart Functions
    suspend fun addToCart(cartItems: CartItems)
    fun getCartItems() : LiveData<List<CartItems>>
    suspend fun deleteCartItems(cartItems: CartItems)
    suspend fun clearAllCart()

    //Local Data Favorites Functions
    suspend fun addToFavorites(product: Product)
    suspend fun deleteFavorites(product: Product)
    suspend fun updateFavorites(product: Product)
    fun getFavoriteItems() : LiveData<List<Product>>
    suspend fun clearAllFavorites()

    //Login
    suspend fun loginUser(user: User) : Resource<Token>

    //User
    suspend fun registerUser(userDetails: UserDetails)
    suspend fun updateUser(userDetails: UserDetails)
    fun getUserDetails() : LiveData<UserDetails>
    suspend fun deleteAllUsers()


}