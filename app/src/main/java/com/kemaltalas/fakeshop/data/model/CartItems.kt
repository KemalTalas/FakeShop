package com.kemaltalas.fakeshop.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cartitems")
data class CartItems(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val image : String,
    val description : String,
    val rate: Rate,
    val title : String,
    val price : String,
    val quantity : Int,
) : Serializable {
}