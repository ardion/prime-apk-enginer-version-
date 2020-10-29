package com.example.enggineraplication.profile

import androidx.lifecycle.ViewModel
import com.example.enggineraplication.portofolio.portofolioApiService
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

class AddPortoViewModel : ViewModel(), CoroutineScope {

    private lateinit var service: portofolioApiService

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main


    fun setLoginService(service: portofolioApiService) {
        this.service = service
    }

    fun postPortoApi(id_worker: RequestBody, name_aplication: RequestBody, link_repository: RequestBody,type_repository: RequestBody, type_portofolio: RequestBody, image: MultipartBody.Part) {
        launch {

            val response = withContext(Dispatchers.IO) {
                try {
                    service?.postPortofolio(id_worker, name_aplication, link_repository, type_repository,type_portofolio , image)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is PortoAddResponse) {
                // Action Success
            }
        }
    }
}