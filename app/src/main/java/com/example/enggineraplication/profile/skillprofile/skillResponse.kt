package com.example.enggineraplication.profile.skillprofile

import com.example.enggineraplication.home.homeresponse
import com.google.gson.annotations.SerializedName

data class skillResponse(val success: Boolean, val message: String?, val data: List<skill>) {

    data class skill(
        @SerializedName("skill") val skill: String?,
        @SerializedName("id_skill") val id_skill: String?
    )
}
