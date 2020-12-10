package com.example.enggineraplication.login


import com.example.enggineraplication.detailworker.detailworkerResponse
import com.example.enggineraplication.login.cekdatauser.cekDataUserResponse
import retrofit2.http.*

interface AuthApiService {

    @FormUrlEncoded
    @POST("regis/login")
    suspend fun loginRequest(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): LoginResponse

    @GET("regis/{id}")
    suspend fun getDataUser(@Path("id") id: String?): cekDataUserResponse
}