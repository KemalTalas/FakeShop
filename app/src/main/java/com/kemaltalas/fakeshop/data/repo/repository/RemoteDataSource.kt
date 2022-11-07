package com.kemaltalas.fakeshop.data.repo.repository

import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.model.Token
import com.kemaltalas.fakeshop.data.model.User
import com.kemaltalas.fakeshop.data.util.Resource
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getAllProducts() : Response<ArrayList<Product>>
    suspend fun getProduct(itemId: Int) : Response<Product>
    suspend fun gelAllCategories() : Response<ArrayList<String>>
    suspend fun getCategoryProducts(category: String) : Response<ArrayList<Product>>

    suspend fun loginUser(user: User) : Response<Token>

}