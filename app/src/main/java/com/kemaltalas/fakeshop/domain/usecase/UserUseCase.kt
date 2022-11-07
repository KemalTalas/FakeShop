package com.kemaltalas.fakeshop.domain.usecase

import com.kemaltalas.fakeshop.data.model.Token
import com.kemaltalas.fakeshop.data.model.User
import com.kemaltalas.fakeshop.data.util.Resource
import com.kemaltalas.fakeshop.data.repo.repository.FakeShopRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val repository: FakeShopRepository
) {

    suspend fun loginUser(user: User) : Resource<Token>{
        return repository.loginUser(user)
    }

}