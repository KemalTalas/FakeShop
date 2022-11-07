package com.kemaltalas.fakeshop.domain.usecase

import androidx.lifecycle.LiveData
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.model.UserDetails
import com.kemaltalas.fakeshop.data.util.Resource
import com.kemaltalas.fakeshop.data.repo.repository.FakeShopRepository
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val repository: FakeShopRepository
) {
    suspend fun getAllProducts() : Resource<ArrayList<Product>>{
        return repository.getAllProducts()
    }

    suspend fun getCategoryItems(category: String) : Resource<ArrayList<Product>>{
        return repository.getCategoryProducts(category)
    }

     fun getUsername() : LiveData<UserDetails>{
        return repository.getUserDetails()
    }

     fun getFavorites() : LiveData<List<Product>> {
        return repository.getFavoriteItems()
    }

}