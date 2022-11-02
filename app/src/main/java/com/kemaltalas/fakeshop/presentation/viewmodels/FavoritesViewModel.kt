package com.kemaltalas.fakeshop.presentation.viewmodels

import androidx.lifecycle.*
import com.kemaltalas.fakeshop.data.model.CartItems
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.util.Resource
import com.kemaltalas.fakeshop.domain.usecase.FavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesUseCase: FavoritesUseCase
) : ViewModel() {

    //Favorites
    val favorites : MutableLiveData<Resource<ArrayList<Product>>> = MutableLiveData()

    val totalItemsPrice : MutableLiveData<Double> = MutableLiveData()

    fun getFavorites() = favoritesUseCase.getFavorites()

    fun addFavorites(product: Product) = viewModelScope.launch { favoritesUseCase.addFavorites(product) }

    fun deleteFavorites(product: Product) = viewModelScope.launch { favoritesUseCase.deleteFavorites(product) }

    fun clearFavorites() = viewModelScope.launch { favoritesUseCase.clearFavoritesItems() }


    //Cart
    fun addToCart(cartItems: CartItems) = viewModelScope.launch { favoritesUseCase.addToCart(cartItems) }

    fun deleteFromCart(cartItems: CartItems) = viewModelScope.launch { favoritesUseCase.deleteFromCart(cartItems) }

    fun getCartItems() = favoritesUseCase.getCartItems()

    fun clearCart() = viewModelScope.launch(IO) { favoritesUseCase.clearCartItems() }

    fun calculateTotal(cartItems: List<CartItems>) = viewModelScope.launch {
        var total = 0.00
        cartItems.forEach { total += it.price.toDouble() }
        totalItemsPrice.postValue(total)
    }

}