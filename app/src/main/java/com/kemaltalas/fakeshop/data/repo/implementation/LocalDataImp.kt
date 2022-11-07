package com.kemaltalas.fakeshop.data.repo.implementation

import androidx.lifecycle.LiveData
import com.kemaltalas.fakeshop.data.local.FakeShopDao
import com.kemaltalas.fakeshop.data.model.CartItems
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.model.UserDetails
import com.kemaltalas.fakeshop.data.repo.repository.LocalDataSource
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

    override suspend fun updateFavoritesItem(product: Product) {
        return dao.updateFavorites(product)
    }

    override suspend fun clearAllFavorites() {
        return dao.clearAllFavorites()
    }

    override suspend fun registerUser(userDetails: UserDetails) {
        return dao.registerUser(userDetails)
    }

    override suspend fun updateUser(userDetails: UserDetails) {
        return dao.updateUser(userDetails)
    }

    override fun getUserDetails(): LiveData<UserDetails> {
        return dao.getUserDetail()
    }

    override suspend fun deleteAllUsers() {
        return dao.deleteAllUser()
    }


}