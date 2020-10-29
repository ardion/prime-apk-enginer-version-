package com.example.enggineraplication.search

import com.google.gson.annotations.SerializedName

data class searchresponse (val success: Boolean, val message: String?, val data: List<worker>) {

    data class worker(
                        @SerializedName("id_worker") val id_worker: String?,
                        @SerializedName("name") val name: String?,
                        @SerializedName("image") val image: String?,
                        @SerializedName("domicile") val domicile: String?,
                        @SerializedName("skill") val skill: String?

    )

}
