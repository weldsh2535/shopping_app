package com.sartor.data.api.request

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class ReviewRequest(

    @SerializedName("product")
    val product: String?,
    @SerializedName("review")
    val review: String?,
    @SerializedName("rate")
    val rate: String?,
    @SerializedName("Image")
    val image: MultipartBody.Part
)
