package com.example.enggineraplication.addskill

import com.google.gson.annotations.SerializedName

data class skillAddResponse(val success: Boolean?, val message: String?, val data: skill) {

    data class skill(
        @SerializedName("id_worker") val id_worker: String?,
        @SerializedName("skill") val skill: String?
    )
}