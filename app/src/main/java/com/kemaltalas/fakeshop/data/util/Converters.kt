package com.kemaltalas.fakeshop.data.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kemaltalas.fakeshop.data.model.Rate
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(
    private val gson : Gson
){

    @TypeConverter
    fun fromRating(rate: Rate): String {
        return gson.toJson(rate)
    }

    @TypeConverter
    fun toRating(json : String) : Rate {
        val type = object : TypeToken<Rate>() {}.type
        return gson.fromJson(json,type)
    }

}