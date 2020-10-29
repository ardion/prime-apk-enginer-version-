package com.example.enggineraplication.detailworker

import com.google.gson.annotations.SerializedName

data class detailworkerResponse(val success: Boolean, val message: String?, val data: DataResult?) {

    data class DataResult(
        @SerializedName("id_worker") val id_worker:String?,
        @SerializedName("id_user") val id_user:String?,
        @SerializedName("jobdesk") val jobdesk:String?,
        @SerializedName("domicile") val domicile:String?,
        @SerializedName("workplace") val workplace:String?,
        @SerializedName("description_personal") val description_personal:String?,
        @SerializedName("job_status") val job_status:String?,
    @SerializedName("instagram") val instagram:String?,
    @SerializedName("github") val github:String?,
    @SerializedName("gitlab") val gitlab:String?,
    @SerializedName("image") val image:String?
    )
}
