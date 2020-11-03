package com.example.enggineraplication.updateprofile

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface updateProfileApiService {
    @Multipart
    @PUT("worker/66")
    suspend fun updateprofileworker(
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
    ): updateProfileResponse

}