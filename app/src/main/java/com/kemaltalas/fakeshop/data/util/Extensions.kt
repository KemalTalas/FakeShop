package com.kemaltalas.fakeshop.data.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import com.kemaltalas.fakeshop.data.model.Product

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

    fun List<Product>.filterBy(count : Int, list: ArrayList<String>) : List<Product>{

        if (count==0){
            TODO()
        }
        TODO()
    }

