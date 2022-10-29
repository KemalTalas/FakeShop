package com.kemaltalas.fakeshop.domain.implementation

import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.remote.ApiService
import com.kemaltalas.fakeshop.data.util.Resource
import com.kemaltalas.fakeshop.domain.repository.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImp @Inject constructor(
    private val apiService: ApiService
    ) : RemoteDataSource {

    override suspend fun getAllProducts(): Response<ArrayList<Product>> {
        return apiService.getAllProducts()
    }

    override suspend fun getProduct(itemId: Int): Response<Product> {
        return apiService.getProduct(itemId)
    }

    override suspend fun gelAllCategories(): Response<ArrayList<String>> {
        return apiService.getCategories()
    }

    override suspend fun getCategoryProducts(category: String): Response<Product> {
        return apiService.getCategoryItem(category)
    }
}