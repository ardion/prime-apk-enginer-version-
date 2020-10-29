package com.example.enggineraplication.detailworker

import com.google.gson.annotations.SerializedName


data class workerdetailModel(
    val id_worker:String,
    val id_user: String,
    val jobdesk: String,
    val domicile: String,
    val workplace: String,
    val description_personal: String,
    val job_status: String,
    val instagram: String,
    val github: String,
    val gitlab:String,
    val image: String
)
