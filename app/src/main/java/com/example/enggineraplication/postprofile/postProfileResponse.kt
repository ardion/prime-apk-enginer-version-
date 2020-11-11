package com.example.enggineraplication.postprofile

import com.google.gson.annotations.SerializedName

data class postProfileResponse(val success: Boolean, val message: String?, val data: profile) {

    data class profile(
        @SerializedName("id") val id: String?,
        @SerializedName("id_user") val id_user: String?,
        val jobdesk: String?,
        @SerializedName("domicile") val domicile: String?,
        val workplace: String?,
        val description_personal: String?,
        val job_status: String?,
        val instagram: String?,
        val github: String?,
        val gitlab: String?,
        val image: String?
    )
}
