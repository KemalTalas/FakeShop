package com.kemaltalas.fakeshop.data.remote

import com.kemaltalas.fakeshop.data.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("products")
    suspend fun getAllProducts() : Response<ArrayList<Product>>

    @GET("products/categories")
    suspend fun getCategories() : Response<ArrayList<String>>

    @GET("products/{id}")
    suspend fun getProduct(@Path(value = "id") itemId: Int ) : Response<Product>

    @GET("products/categories/{category}")
    suspend fun getCategoryItem(@Path(value = "category") category: String) : Response<Product>
}