package com.example.enggineraplication.profile

import androidx.lifecycle.ViewModel
import com.example.enggineraplication.experience.experienceApiService
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

class AddExperienceViewModel : ViewModel(), CoroutineScope {

    private lateinit var service: experienceApiService

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main


    fun setLoginService(service: experienceApiService) {
        this.service = service
    }


    fun postExperienceApi(id_worker: String, position: String, company_name: String, date: String,description_work:String) {
        launch {

            val response = withContext(Dispatchers.IO) {
                try {
                    service?.postexperience(id_worker, position,  date, company_name, description_work)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is experienceAddResponse) {
                // Action Success
            }
        }
    }
}