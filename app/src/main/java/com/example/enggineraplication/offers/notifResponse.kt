package com.example.enggineraplication.offers

import com.google.gson.annotations.SerializedName


data class notifResponse (val success: Boolean, val message: String?, val data: List<notif>) {
    data class notif(
        @SerializedName("image") val image: String?,
        @SerializedName("id_company") val id_company: String?,
        @SerializedName("company_name") val company_name: String?,
        @SerializedName("name_project") val name_project: String?,
        @SerializedName("order_worker") val order_worker: String?,
        @SerializedName("id_project") val id_project: String?,
        @SerializedName("message") val message: String?,
        @SerializedName("price") val price: String?,
        @SerializedName("project_job") val project_job: String?,
        @SerializedName("status") val status: String?

    )

}




