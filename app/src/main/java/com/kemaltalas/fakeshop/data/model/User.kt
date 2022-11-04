package com.kemaltalas.fakeshop.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username")
    val username : String,
    @SerializedName("password")
    val password : String
)
data class Token(
    val token : String
)