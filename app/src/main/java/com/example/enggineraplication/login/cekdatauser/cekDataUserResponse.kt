package com.example.enggineraplication.login.cekdatauser

import com.google.gson.annotations.SerializedName


data class cekDataUserResponse(val success: Boolean, val message: String?, val data: DataResult?) {

    data class DataResult(
        @SerializedName("id_worker") val id_worker:String?
    )
}