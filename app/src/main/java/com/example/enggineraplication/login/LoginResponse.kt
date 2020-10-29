package com.example.enggineraplication.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(val success: Boolean, val message: String?, val data: DataResult?) {

    data class DataResult(
        @SerializedName("id_user") val id:String?,
        @SerializedName("name") val username:String?,
        @SerializedName("email") val email:String?,
        @SerializedName("password") val password:String?,
        @SerializedName("user_role") val role:String?,
        @SerializedName("user_status") val status:String?,
        @SerializedName("token") val token:String?
    )
}
