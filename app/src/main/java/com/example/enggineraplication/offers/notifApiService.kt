package com.example.enggineraplication.offers

import com.example.enggineraplication.detailworker.detailworkerResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface notifapiservice {
    @GET("projectman/{id}")
    suspend fun getAllNotif( @Path("id") id: String?): notifResponse
}
