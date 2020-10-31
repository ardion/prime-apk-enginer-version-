package com.example.enggineraplication.postprofile

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import kotlin.coroutines.CoroutineContext

class postProfileViewModel : ViewModel(), CoroutineScope {

    private lateinit var service: postProfileApiService

    //    lateinit var sharedPref: PreferenceHelper
//    sharedPref= PreferenceHelper(this)
//    val dataIDcompany = MutableLiveData<String>()


    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main


    fun setLoginService(service: postProfileApiService) {
        this.service = service
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
                        id_user, jobdesk, domicile, workplace, job_status, instagram, github, gitlab, description_personal, image
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is postProfileResponse) {
//                var list = response.data.id
//                Log.d("responid",response.data.id.toString())
//                dataIDcompany.value = list
            }

        }


    }
}

