package com.example.enggineraplication.portofolio


import com.example.enggineraplication.profile.PortoAddResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface portofolioApiService {

    @GET("portofolio/{id}")
    suspend fun getAllPorto(@Path("id") id: String?) : portoresponse

    @Multipart
    @POST("portofolio")
    suspend fun postPortofolio(
        @Part("id_worker") id_worker: RequestBody,
        @Part("name_aplication") name_aplication: RequestBody,
        @Part("link_repository") link_repository: RequestBody,
        @Part("type_repository") type_repository: RequestBody,
        @Part("type_portofolio") type_portofolio: RequestBody,
        @Part image: MultipartBody.Part
    ): PortoAddResponse


}


