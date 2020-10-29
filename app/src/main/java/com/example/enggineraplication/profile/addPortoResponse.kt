package com.example.enggineraplication.profile

import com.google.gson.annotations.SerializedName


data class PortoAddResponse(val success: String?, val message: String?, val data: Portofolio) {

    data class Portofolio(
        @SerializedName("id_worker") val id_worker: String?,
        @SerializedName("name_aplication") val name_aplication: String?,
        @SerializedName("link_repository") val link_repository: String?,
        @SerializedName("type_repository") val type_repository: String?,
        @SerializedName("type_portofolio") val type_portofolio: String?,
        val image: String?

    )
}
