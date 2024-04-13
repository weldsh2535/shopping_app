package com.sartor.data.api.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ReviewResponse(

    @SerializedName("product")
    val product: ProductBaseResponse,
    @SerializedName("review")
    val review: String,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("img")
    val img: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("customer")
    val customer: CustomersResponse,
    @SerializedName("created_by")
    val created_by: CreatedByResponse
)
