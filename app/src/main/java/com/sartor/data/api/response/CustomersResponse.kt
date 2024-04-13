package com.sartor.data.api.response

import com.google.gson.annotations.SerializedName


data class CustomersResponse(
    @SerializedName("fullname") var fullname: String? = null,
    @SerializedName("picture") var picture: String? = null,
    @SerializedName("favorite") var favorite: ArrayList<String> = arrayListOf(),
    @SerializedName("reviews") var reviews: ArrayList<String> = arrayListOf(),
    @SerializedName("tags") var tags: ArrayList<String> = arrayListOf(),
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("preference") var preference: Boolean? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("user") var user: String? = null,
    @SerializedName("email") var email: String? = null

)