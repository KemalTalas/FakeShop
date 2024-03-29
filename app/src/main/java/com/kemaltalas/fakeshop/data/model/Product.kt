package com.kemaltalas.fakeshop.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites")
data class Product(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id : Int,
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("title")
    val title: String,
    var isFavorited : Boolean,
    @SerializedName("rating")
    val rating: Rate

) : java.io.Serializable

