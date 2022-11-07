package com.kemaltalas.fakeshop.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemaltalas.fakeshop.data.model.CartItems
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.domain.usecase.FavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val favoritesUseCase: FavoritesUseCase
) : ViewModel() {

    fun addFavorites(product: Product) = viewModelScope.launch(IO) {
        favoritesUseCase.addFavorites(product)
    }

    fun updateFavoritesItem(product: Product, isLiked : Boolean) = viewModelScope.launch {
        val copy = product.copy(isFavorited = isLiked)
        favoritesUseCase.updateFavorites(copy)
    }

    fun deleteFavorites(product: Product) = viewModelScope.launch(IO) { favoritesUseCase.deleteFavorites(product) }

    fun addToCart(cartItems: CartItems) = viewModelScope.launch(IO) { favoritesUseCase.addToCart(cartItems) }

}