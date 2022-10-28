package com.kemaltalas.fakeshop.data.remote

import com.kemaltalas.fakeshop.data.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("products")
    suspend fun getAllProducts() : Response<ArrayList<Product>>

    @GET("products/categories")
    suspend fun getCategories() : Response<ArrayList<String>>

}