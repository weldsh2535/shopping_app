package com.sartor.data.api.response


import com.google.gson.annotations.SerializedName


data class CreatedByResponse(
    @SerializedName("archived")
    val archived: Boolean,
    @SerializedName("archived_at")
    val archivedAt: Any,
    @SerializedName("corporate_activation_status")
    val corporateActivationStatus: Boolean,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("internal")
    val `internal`: ProductResponse.CreatedBy.Internal,
    @SerializedName("is_registered")
    val isRegistered: Boolean,
    @SerializedName("role")
    val role: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("username")
    val username: String
)