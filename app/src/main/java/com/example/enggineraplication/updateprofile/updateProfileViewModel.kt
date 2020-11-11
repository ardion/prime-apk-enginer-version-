package com.example.enggineraplication.updateprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.enggineraplication.postprofile.postProfileApiService
import com.example.enggineraplication.postprofile.postProfileResponse
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

class updateProfileViewModel : ViewModel(), CoroutineScope {

    private lateinit var service: updateProfileApiService
    val isLoadingProgressBarLiveData = MutableLiveData<Boolean>()

    //    lateinit var sharedPref: PreferenceHelper
//    sharedPref= PreferenceHelper(this)
//    val dataIDcompany = MutableLiveData<String>()


    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main


    fun setLoginService(service: updateProfileApiService) {
        this.service = service
    }

    fun updateprofileworker(
        id:String,
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
            isLoadingProgressBarLiveData.value=true

            val response = withContext(Dispatchers.IO) {
                try {


                    service?.updateprofileworker(
                        id, id_user, jobdesk, domicile, workplace, job_status, instagram, github, gitlab, description_personal, image
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is updateProfileResponse) {
//                var list = response.data.id
//                Log.d("responid",response.data.id.toString())
//                dataIDcompany.value = list
            }
            isLoadingProgressBarLiveData.value=false

        }


    }
}

