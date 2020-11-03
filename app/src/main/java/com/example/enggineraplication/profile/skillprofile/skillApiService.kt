package com.example.enggineraplication.profile.skillprofile

import com.example.enggineraplication.addskill.skillAddResponse
import retrofit2.http.*

interface skillApiService {

    @GET("skill/{id}")
    suspend fun getAllSkill(@Path("id") id: String?) : skillResponse

    @FormUrlEncoded
    @POST("skill")
    suspend fun postSkill(@Field("id_worker") id_worker: String?,
                             @Field("skill") skill: String?) : skillAddResponse
}

