package com.example.enggineraplication.experience

import com.google.gson.annotations.SerializedName

data class experienceresponse (val success: Boolean, val message: String?, val data: List<experience>) {

    data class experience(
        @SerializedName("id_worker") val id_worker: String?,
        @SerializedName("position") val position: String?,
        @SerializedName("company_name") val company_name: String?,
        @SerializedName("description_work") val description_work: String?,
        @SerializedName("date") val date: String?


    )

}

