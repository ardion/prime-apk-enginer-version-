package com.example.enggineraplication.home

import retrofit2.http.*

interface homeapiservice {
    @GET("worker/home/home")
    suspend fun getAllWorker() : homeresponse


}
