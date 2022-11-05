package com.kemaltalas.fakeshop.data.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import com.kemaltalas.fakeshop.data.model.Product
import javax.inject.Inject


fun Fragment.hideKeyboards() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


class SharedPrefs @Inject constructor(private val sharedPreferences: SharedPreferences){

    fun userLoginSP() : Boolean{
        val token = sharedPreferences.getString(Constants.LOGIN_TOKEN,null)
        return token != null
    }

    fun saveLoginToken(token: String){
        sharedPreferences.edit().putString(Constants.LOGIN_TOKEN,null)
    }

    fun userLogoutSP() : Boolean{
        sharedPreferences.edit().remove(Constants.LOGIN_TOKEN).apply()
        return userLoginSP()
    }

    fun sharedPrefToken() : String{
        val tokenStr = sharedPreferences.getString(Constants.LOGIN_TOKEN,null)
        return tokenStr.toString()
    }



}


