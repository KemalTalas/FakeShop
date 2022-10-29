package com.kemaltalas.fakeshop.domain.repository

import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.util.Resource
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getAllProducts() : Response<ArrayList<Product>>
    suspend fun getProduct(itemId: Int) : Response<Product>
    suspend fun gelAllCategories() : Response<ArrayList<String>>
    suspend fun getCategoryProducts(category: String) : Response<Product>

}