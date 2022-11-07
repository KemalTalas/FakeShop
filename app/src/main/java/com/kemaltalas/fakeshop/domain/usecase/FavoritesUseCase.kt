package com.kemaltalas.fakeshop.domain.usecase

import androidx.lifecycle.LiveData
import com.kemaltalas.fakeshop.data.model.CartItems
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.repo.repository.FakeShopRepository
import javax.inject.Inject

class FavoritesUseCase @Inject constructor(
    private val repository: FakeShopRepository
) {

    fun getFavorites() : LiveData<List<Product>> {
        return repository.getFavoriteItems()
    }
    suspend fun addFavorites(product: Product){
        return repository.addToFavorites(product)
    }
    suspend fun deleteFavorites(product: Product){
        return repository.deleteFavorites(product)
    }

    suspend fun updateFavorites(product: Product){
        return repository.updateFavorites(product)
    }

    suspend fun clearFavoritesItems(){
        return repository.clearAllFavorites()
    }

    suspend fun addToCart(cartItems: CartItems){
        return repository.addToCart(cartItems)
    }

    suspend fun deleteFromCart(cartItems: CartItems){
        return repository.deleteCartItems(cartItems)
    }

    fun getCartItems() : LiveData<List<CartItems>> {
        return repository.getCartItems()
    }

    suspend fun clearCartItems(){
        return repository.clearAllCart()
    }

}