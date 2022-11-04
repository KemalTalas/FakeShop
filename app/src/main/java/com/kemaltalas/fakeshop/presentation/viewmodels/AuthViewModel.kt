package com.kemaltalas.fakeshop.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.kemaltalas.fakeshop.data.model.UserDetails
import com.kemaltalas.fakeshop.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel(){

    val getPerson : LiveData<UserDetails> = authUseCase.getUserDetails()

    fun registerUser(userDetails: UserDetails) = viewModelScope.launch(Dispatchers.IO) {
        authUseCase.registerUser(userDetails)
    }

    fun updateUser(userDetails: UserDetails) = viewModelScope.launch(Dispatchers.IO) {
        authUseCase.updateUser(userDetails)
    }

    fun getUserDetail() = liveData<UserDetails> {
         authUseCase.getUserDetails()
    }

    fun deleteAllUsers() = viewModelScope.launch(Dispatchers.IO) {
        authUseCase.deleteAllUsers()
    }

}