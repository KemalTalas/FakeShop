package com.kemaltalas.fakeshop.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemaltalas.fakeshop.data.model.Product
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

    fun getAllProducts() = viewModelScope.launch {
       try {
           products.postValue(Resource.Loading())
           val apiResult = productUseCase.getAllProducts()
           products.postValue(apiResult)
       }catch (e: Exception){
           products.postValue(Resource.Error(message = "Error"))
       }
    }

}