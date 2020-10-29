package com.example.enggineraplication.portofolio
import com.google.gson.annotations.SerializedName

data class portoresponse (val success: Boolean, val message: String?, val data: List<porto>) {

    data class porto(
        @SerializedName("id_worker") val id_worker: String?,
        @SerializedName("name_aplication") val name_aplication: String?,
        @SerializedName("link_repository") val link_repository: String?,
        @SerializedName("type_repository") val type_repository: String?,
        @SerializedName("type_portofolio") val type_portofolio: String?,
        @SerializedName("image") val image: String?

    )

}

