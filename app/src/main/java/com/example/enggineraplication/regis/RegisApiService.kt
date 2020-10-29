package com.example.enggineraplication.regis

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisApiService {

    @FormUrlEncoded
    @POST("regis/register/")
    suspend fun registerRequest(
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("number_phone") phone: String?
    ): RegisResponse

}