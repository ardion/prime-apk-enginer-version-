package com.example.enggineraplication.experience

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.enggineraplication.Constant
import com.example.enggineraplication.PreferenceHelper
import com.example.enggineraplication.R
import com.example.enggineraplication.databinding.ActivityExperienceBinding
import com.example.enggineraplication.login.ApiClient
import kotlinx.coroutines.*

class experienceActivity : AppCompatActivity() {
    lateinit var binding: ActivityExperienceBinding
    lateinit var sharedPref: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref= PreferenceHelper(this)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_experience
        )
        binding.recyclerView.adapter = experienceAdabter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        useCoroutineToCallAPI()

    }

    private fun useCoroutineToCallAPI() {
//        binding.progressBar.visibility = View.VISIBLE
        val service = ApiClient.getApiClient(this)?.create(experienceApiService::class.java)
        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {

            binding.progressBar.visibility = View.VISIBLE
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    service?.getAllExperience(sharedPref.getString(Constant.PREF_IDWORKER))
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is experienceresponse) {
                Log.d("android1", response.data.toString())
                val list = response.data?.map {
                    experienceModel(
                        it.id_worker.orEmpty(),
                        it.position.orEmpty(),
                        it.company_name.orEmpty(),
                        it.description_work.orEmpty(),
                        it.date.orEmpty()
                    )
                } ?: listOf()

                (binding.recyclerView.adapter as experienceAdabter).addList(list)
            } else if (response is Throwable) {
                Log.e("android1", response.message ?: "Error")
            }
            binding.progressBar.visibility = View.GONE
            Log.d("android1", "finish : ${Thread.currentThread().name}")
        }


    }

}