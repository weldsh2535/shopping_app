package com.sartor.data.api.response

import com.google.gson.annotations.SerializedName

class ProductBaseResponse (
        @SerializedName("created_at"  ) var createdAt   : String? = null,
        @SerializedName("_id"         ) var Id          : String? = null,
        @SerializedName("description" ) var description : String? = null,
        @SerializedName("name"        ) var name        : String? = null,
        @SerializedName("store"       ) var store       : String? = null,
        @SerializedName("price"       ) var price       : Int?    = null,
        @SerializedName("category"    ) var category    : String? = null,
        @SerializedName("brands"      ) var brands      : String? = null,
        @SerializedName("qty"         ) var qty         : Int?    = null,
        @SerializedName("created_by"  ) var createdBy   : String? = null,
        @SerializedName("img"         ) var img         : String? = null


    )