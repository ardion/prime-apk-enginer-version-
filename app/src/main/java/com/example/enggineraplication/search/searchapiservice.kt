package com.example.enggineraplication.search

import com.example.enggineraplication.search.searchModel
import retrofit2.http.*

val items = mutableListOf<searchModel>()
interface searchapiservice {
    @GET("worker")
    suspend fun getAllSearchSkill( @Query("limit") limit: Int?, @Query("search[skill]") search: String?) : searchresponse

    @GET("worker")
    suspend fun getAllSearchname( @Query("limit") limit: Int?, @Query("search") search: String?) : searchresponse

    @GET("worker")
    suspend fun getAllSearchDomicile( @Query("limit") limit: Int?, @Query("search[domicile]") search: String?) : searchresponse


}


//"[skill]=$search"