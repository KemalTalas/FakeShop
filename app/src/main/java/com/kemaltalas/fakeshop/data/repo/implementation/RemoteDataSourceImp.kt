package com.kemaltalas.fakeshop.data.repo.implementation

import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.model.Token
import com.kemaltalas.fakeshop.data.model.User
import com.kemaltalas.fakeshop.data.remote.ApiService
import com.kemaltalas.fakeshop.data.util.Resource
import com.kemaltalas.fakeshop.data.repo.repository.RemoteDataSource
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

    override suspend fun getCategoryProducts(category: String): Response<ArrayList<Product>> {
        return apiService.getCategoryItem(category)
    }

    override suspend fun loginUser(user: User): Response<Token> {
        return apiService.loginUser(user)
    }
}