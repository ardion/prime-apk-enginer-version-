package com.example.enggineraplication.profile

import com.google.gson.annotations.SerializedName

data class skillResponse(val success: Boolean, val message: String?, val data: skill) {

    data class skill(
        @SerializedName("skill") val skill: String?
    )
}

