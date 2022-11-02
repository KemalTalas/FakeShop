package com.kemaltalas.fakeshop.domain.implementation

import androidx.lifecycle.LiveData
import com.kemaltalas.fakeshop.data.local.FakeShopDao
import com.kemaltalas.fakeshop.data.model.CartItems
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.domain.repository.LocalDataSource
import javax.inject.Inject

class LocalDataImp @Inject constructor(
    private val dao: FakeShopDao
)   : LocalDataSource {
    override suspend fun addToCart(cartItems: CartItems) {
        return dao.addToCart(cartItems)
    }

    override fun getCartItems(): LiveData<List<CartItems>> {
       return dao.cartItems()
    }


    override suspend fun deleteCartItems(cartItems: CartItems) {
        return dao.deleteCartItems(cartItems)
    }

    override suspend fun clearAllCart() {
        return dao.clearAllCart()
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

    override suspend fun clearAllFavorites() {
        return dao.clearAllFavorites()
    }


}