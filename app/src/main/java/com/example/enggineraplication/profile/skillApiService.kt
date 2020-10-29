package com.example.enggineraplication.profile

import com.example.enggineraplication.portofolio.portoresponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface skillApiService {

    @GET("skill/{id}")
    suspend fun getAllSkill(@Path("id") id: String?) : skillResponse

}

