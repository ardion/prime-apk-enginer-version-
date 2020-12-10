package com.example.enggineraplication.addskill

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.enggineraplication.profile.experienceAddResponse
import com.example.enggineraplication.profile.skillprofile.skillApiService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AddSkillViewModel : ViewModel(), CoroutineScope {

    private lateinit var service: skillApiService
    val isLoginLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setLoginService(service: skillApiService) {
        this.service = service
    }

    fun postSKillApi(id_worker: String, skill: String) {
        launch {
            isLoginLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service?.postSkill(id_worker, skill)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is skillAddResponse) {

            }
            isLoginLiveData.value = false
        }
    }
}
