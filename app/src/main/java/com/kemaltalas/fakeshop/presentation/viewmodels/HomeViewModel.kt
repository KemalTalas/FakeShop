package com.kemaltalas.fakeshop.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.kemaltalas.fakeshop.data.model.Product
import com.kemaltalas.fakeshop.data.model.UserDetails
import com.kemaltalas.fakeshop.data.util.Resource
import com.kemaltalas.fakeshop.domain.usecase.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val app: Application,
    private val productUseCase: ProductUseCase
) : AndroidViewModel(app) {

    val products : MutableLiveData<Resource<ArrayList<Product>>> = MutableLiveData()

    val user : LiveData<UserDetails> = productUseCase.getUsername()

    val favorites : LiveData<List<Product>> = productUseCase.getFavorites()

    fun getAllProducts() = viewModelScope.launch {
       try {
           products.postValue(Resource.Loading())
           val apiResult = productUseCase.getAllProducts()
           products.postValue(apiResult)
       }catch (e: Exception){
           products.postValue(Resource.Error(message = "Error"))
       }
    }

    fun getCategoryProducts(categoryName: String) = viewModelScope.launch {
        if(categoryName != "All"){
            products.postValue(Resource.Loading())
            try {
                    val apiResult1 = productUseCase.getCategoryItems(categoryName)
                    products.postValue(apiResult1)

            }catch (e : Exception){
                products.postValue(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
            }
        }else{
            getAllProducts()
        }
    }

}