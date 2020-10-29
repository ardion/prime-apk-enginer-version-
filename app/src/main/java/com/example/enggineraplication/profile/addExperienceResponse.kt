package com.example.enggineraplication.profile

import com.google.gson.annotations.SerializedName

data class experienceAddResponse(val success: String?, val message: String?, val data: experience) {

    data class experience(
        @SerializedName("id_worker") val id_worker: String?,
        @SerializedName("position") val position: String?,
        @SerializedName("company_name") val company_name: String?,
        @SerializedName("date") val date: String?,
        @SerializedName("description_work") val description_work: String?

    )
}

