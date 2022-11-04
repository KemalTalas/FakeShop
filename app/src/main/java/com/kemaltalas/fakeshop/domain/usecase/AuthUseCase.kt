package com.kemaltalas.fakeshop.domain.usecase

import androidx.lifecycle.LiveData
import com.kemaltalas.fakeshop.data.model.UserDetails
import com.kemaltalas.fakeshop.domain.repository.FakeShopRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: FakeShopRepository
) {

    suspend fun registerUser(userDetails: UserDetails){
        return repository.registerUser(userDetails)
    }
    suspend fun updateUser(userDetails: UserDetails){
        return repository.updateUser(userDetails)
    }
    fun getUserDetails(): LiveData<UserDetails> {
        return repository.getUserDetails()
    }

    suspend fun deleteAllUsers(){
        return repository.deleteAllUsers()
    }

}