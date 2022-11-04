package com.kemaltalas.fakeshop.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.kemaltalas.fakeshop.data.model.Token
import com.kemaltalas.fakeshop.data.model.User
import com.kemaltalas.fakeshop.data.util.Constants
import com.kemaltalas.fakeshop.data.util.Resource
import com.kemaltalas.fakeshop.data.util.SharedPrefs
import com.kemaltalas.fakeshop.domain.usecase.UserUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    var token = ""

    val loginResponse : MutableLiveData<Resource<Token>> = MutableLiveData()
    val isLogged : Boolean = sharedPrefs.userLoginSP()
    val tokenStr : String = sharedPrefs.sharedPrefToken()

    fun loginUser(user: User) = viewModelScope.launch {
        try {
            loginResponse.postValue(Resource.Loading())
            val apiResult = userUseCase.loginUser(user)
            loginResponse.postValue(apiResult)
            apiResult.let { sharedPreferences.edit().putString(Constants.LOGIN_TOKEN,it.data?.token) }
        }catch (e: Exception){
            loginResponse.postValue(Resource.Error("HATA"))
        }
    }

    fun saveToken(token : String) {
        sharedPrefs.saveLoginToken(token)
    }
}