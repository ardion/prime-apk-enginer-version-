package com.example.enggineraplication.transaction

import com.example.enggineraplication.detailworker.detailworkerResponse
import com.example.enggineraplication.login.LoginResponse
import retrofit2.http.*

interface transactionApiService {

    @FormUrlEncoded
    @PATCH("projectman/{id}")
    suspend fun patchtransaction( @Path("id") id: String?,@Field("status") status: String?): transactionResponse
}
