package com.sartor.data.api.response


import com.google.gson.annotations.SerializedName

data class AddCartResponse(
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("product") var product: Product,
    @SerializedName("created_by") var createdBy: CreatedBy,

    ) {
    data class Product(

        @SerializedName("created_at") var createdAt: String? = null,
        @SerializedName("_id") var Id: String? = null,
        @SerializedName("description") var description: String? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("store") var store: String? = null,
        @SerializedName("price") var price: Int? = null,
        @SerializedName("category") var category: Category? = Category(),
        @SerializedName("brands") var brands: Brands? = Brands(),
        @SerializedName("qty") var qty: Int? = null,
        @SerializedName("created_by") var createdBy: String? = null,
        @SerializedName("img") var img: Img? = Img()

    ) {
        data class Img(

            @SerializedName("created_at") var createdAt: String? = null,
            @SerializedName("_id") var Id: String? = null,
            @SerializedName("created_by") var createdBy: String? = null,
            @SerializedName("img0") var img0: String? = null,
            @SerializedName("img1") var img1: String? = null,
            @SerializedName("img2") var img2: String? = null,
            @SerializedName("img3") var img3: String? = null,
            @SerializedName("img4") var img4: String? = null,
            @SerializedName("img5") var img5: String? = null,
            @SerializedName("img6") var img6: String? = null,
            @SerializedName("img7") var img7: String? = null

        )

        data class Brands(

            @SerializedName("followers") var followers: ArrayList<String> = arrayListOf(),
            @SerializedName("likes") var likes: ArrayList<String> = arrayListOf(),
            @SerializedName("is_top") var isTop: Boolean? = null,
            @SerializedName("created_at") var createdAt: String? = null,
            @SerializedName("_id") var Id: String? = null,
            @SerializedName("img") var img: String? = null,
            @SerializedName("title") var title: String? = null

        )

        data class Category(

            @SerializedName("archived") var archived: Boolean? = null,
            @SerializedName("archived_at") var archivedAt: String? = null,
            @SerializedName("created_at") var createdAt: String? = null,
            @SerializedName("updated_at") var updatedAt: String? = null,
            @SerializedName("_id") var Id: String? = null,
            @SerializedName("name") var name: String? = null,
            @SerializedName("created_by") var createdBy: String? = null

        )

    }

    data class CreatedBy(

        @SerializedName("is_registered") var isRegistered: Boolean? = null,
        @SerializedName("status") var status: String? = null,
        @SerializedName("corporate_activation_status") var corporateActivationStatus: Boolean? = null,
        @SerializedName("archived") var archived: Boolean? = null,
        @SerializedName("archived_at") var archivedAt: String? = null,
        @SerializedName("_id") var Id: String? = null,
        @SerializedName("username") var username: String? = null,
        @SerializedName("role") var role: String? = null,
        @SerializedName("customers") var customers: String? = null,
        @SerializedName("last_login") var lastLogin: String? = null

    )
}