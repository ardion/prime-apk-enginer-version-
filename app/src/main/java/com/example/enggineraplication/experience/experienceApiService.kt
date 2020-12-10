package com.example.enggineraplication.experience

import com.example.enggineraplication.profile.addExperienceActivity
import com.example.enggineraplication.profile.experienceAddResponse
import retrofit2.http.*


interface experienceApiService {

    @GET("experience/{id}")
    suspend fun getAllExperience(@Path("id") id: String?): experienceresponse

    @FormUrlEncoded
    @POST("experience")
    suspend fun postexperience(
        @Field("id_worker") id_worker: String?,
        @Field("position") position: String?,
        @Field("company_name") company_namer: String?,
        @Field("date") date: String?,
        @Field("description_work") description_work: String?
    ): experienceAddResponse


}


