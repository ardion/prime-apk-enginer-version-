package com.example.enggineraplication.postprofile

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.enggineraplication.Constant
import com.example.enggineraplication.PreferenceHelper
import com.example.enggineraplication.parentActivity
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import java.math.MathContext
import kotlin.coroutines.CoroutineContext

class postProfileViewModel : ViewModel(), CoroutineScope {

    private lateinit var service: postProfileApiService

    lateinit var sharedPref: PreferenceHelper
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main


    fun setLoginService(service: postProfileApiService) {
        this.service = service
    }

    fun getSharedPreference(mContex: Context) {
        sharedPref = PreferenceHelper(mContex)
    }


    fun postProfileApi(
        id_user: RequestBody,
        jobdesk: RequestBody,
        domicile: RequestBody,
        workplace: RequestBody,
        job_status: RequestBody,
        instagram: RequestBody,
        github: RequestBody,
        gitlab: RequestBody,
        description_personal: RequestBody,
        image: MultipartBody.Part
    ) {

        launch {

            val response = withContext(Dispatchers.IO) {
                try {


                    service?.postprofile(
                        id_user,
                        jobdesk,
                        domicile,
                        workplace,
                        job_status,
                        instagram,
                        github,
                        gitlab,
                        description_personal,
                        image
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is postProfileResponse) {
                sharedPref.put(Constant.PREF_IDWORKERP, response.data.id.toString())


            }

        }


    }
}

