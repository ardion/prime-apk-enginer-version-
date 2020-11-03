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

            val response = withContext(Dispatchers.IO) {
                try {
                    service?.postSkill(id_worker, skill)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is skillAddResponse) {
                // Action Success
                isLoginLiveData.value = true
            }
            isLoginLiveData.value = false
        }
    }
}


//class LoginViewModel : ViewModel(), CoroutineScope {
//
//    val isLoginLiveData = MutableLiveData<Boolean>()
//
//    private lateinit var service: AuthApiService
//    private lateinit var sharedPreferences: SharedPreferences
//
//    override val coroutineContext: CoroutineContext
//        get() = Job() + Dispatchers.Main
//
//    fun setSharedPreference(sharedPreferences: SharedPreferences) {
//        this.sharedPreferences = sharedPreferences
//    }
//
//    fun setLoginService(service: AuthApiService) {
//        this.service = service
//    }
//
//    fun callLoginApi(email: String, password: String) {
//        launch {
//
//            val response = withContext(Dispatchers.IO) {
//                try {
//                    service?.loginRequest(email, password)
//                } catch (e: Throwable) {
//                    e.printStackTrace()
//
//                    withContext(Dispatchers.Main) {
//                        isLoginLiveData.value = false
//                    }
//
//                }
//            }
//
//            if (response is LoginResponse) {
//                if (response.data?.role == "1") {
//                    // SAVE TOKEN TO SHARED PREF
//                    sharedPreferences.edit().putString(LearnSharedPrefActivity.KEY_TOKEN, response.data.token).apply()
//                    // SAVE LOGIN DATA SHARED PREF
//
//                    isLoginLiveData.value = true
//                } else {
//                    isLoginLiveData.value = false
//                }
//            }
//        }
//    }
//}