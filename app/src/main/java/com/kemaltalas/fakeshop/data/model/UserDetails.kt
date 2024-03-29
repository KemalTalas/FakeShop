package com.kemaltalas.fakeshop.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userdetail")
data class UserDetails(

    val firstname: String,
    val lastname: String,
    val username: String,
    val password : String,
    val email : String,
    val phoneNumber: String,
    val city : String,
    val country : String,
    val addressLine : String,
    val secureAnswer : String,

){
    @PrimaryKey(autoGenerate = false)
    var id : Int = 0
}