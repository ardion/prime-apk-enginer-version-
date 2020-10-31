package com.example.enggineraplication.postprofile

import com.example.enggineraplication.updateprofile.updateProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface postProfileApiService {

//    @GET("company/{id}")
//    suspend fun getCompanybyID( @Path("id") id: String?) : companyResponse

    @Multipart
    @POST("worker")
    suspend fun postprofile(
        @Part("id_user") id_user: RequestBody,
        @Part("jobdesk") jobdesk: RequestBody,
        @Part("domicile") domicile: RequestBody,
        @Part("workplace") workplace: RequestBody,
        @Part("job_status") job_status: RequestBody,
        @Part("instagram") instagram: RequestBody,
        @Part("github") github: RequestBody,
        @Part("gitlab") gitlab: RequestBody,
        @Part("description_personal") description_personal: RequestBody,
        @Part image: MultipartBody.Part
    ): postProfileResponse



}

