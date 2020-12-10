package com.example.enggineraplication.home

import retrofit2.http.*


interface homeapiservice2 {
    @GET("worker/home/home")
    suspend fun getAllWorker2() : homeresponse2
}