package com.kemaltalas.fakeshop.domain.implementation

import androidx.lifecycle.LiveData
import com.kemaltalas.fakeshop.data.local.FakeShopDao
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.domain.repository.LocalDataSource
import javax.inject.Inject

class LocalDataImp @Inject constructor(
    private val dao: FakeShopDao
)   : LocalDataSource {
    override suspend fun addToCart(product: Product) {
        return dao.addToCart(product)
    }

    override fun getCartItems(): LiveData<List<Product>> {
       return dao.cartItems()
    }

//    override suspend fun updateCartItems(product: Product) {
//        return dao.
//    }

    override suspend fun deleteCartItems(product: Product) {
        return dao.deleteCartItems(product)
    }

    override suspend fun addToFavorites(product: Product) {
        return dao.addToFavorites(product)
    }

    override fun getFavoritesItems(): LiveData<List<Product>> {
        return dao.favoriteItems()
    }

    override suspend fun deleteFavoritesItem(product: Product) {
        return dao.deleteFavorites(product)
    }


}