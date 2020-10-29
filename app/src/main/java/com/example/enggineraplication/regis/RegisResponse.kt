package com.example.enggineraplication.regis

import com.google.gson.annotations.SerializedName

data class RegisResponse(val success: Boolean, val message: String?, val data: DataResult?) {

    data class DataResult(
        @SerializedName("id") val id: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("email") val email: String?,
        @SerializedName("user_role") val role: String?,
        @SerializedName("user_status") val status: String?,
        @SerializedName("created_at") val createdAt: String?
    )
}