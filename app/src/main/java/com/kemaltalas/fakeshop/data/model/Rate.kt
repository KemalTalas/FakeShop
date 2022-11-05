package com.kemaltalas.fakeshop.data.model

import com.google.gson.annotations.SerializedName

data class Rate(    @SerializedName("count")
                    val count: Int,
                    @SerializedName("rate")
                    val rate : Double )