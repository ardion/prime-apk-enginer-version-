package com.example.enggineraplication.detailworker


import retrofit2.http.GET
import retrofit2.http.Path

interface detailworkerapiservice {
    @GET("worker/{id}")
    suspend fun getAllWorker( @Path("id") id: String?): detailworkerResponse
}

