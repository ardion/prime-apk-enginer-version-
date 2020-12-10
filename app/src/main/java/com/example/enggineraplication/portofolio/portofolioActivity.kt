package com.example.enggineraplication.portofolio

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
import com.example.enggineraplication.databinding.ActivityPortofolioBinding
import com.example.enggineraplication.databinding.FragmentHomeBinding
import com.example.enggineraplication.login.ApiClient
import kotlinx.coroutines.*

class portofolioActivity : AppCompatActivity() {
    lateinit var binding: ActivityPortofolioBinding
    private lateinit var RecycleWorker: portoAdabter
    lateinit var sharedPref: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portofolio)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_portofolio)
        sharedPref = PreferenceHelper(this)

        binding.recyclerView.adapter = portoAdabter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        useCoroutineToCallAPI()

    }

    private fun useCoroutineToCallAPI() {
        val service = ApiClient.getApiClient(this)?.create(portofolioApiService::class.java)
        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {

            binding.progressBar.visibility = View.VISIBLE
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    val a = sharedPref.getString(Constant.PREF_IDWORKER)
                    service?.getAllPorto(a)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is portoresponse) {
                Log.d("android1", response.data.toString())
                val list = response.data?.map {
                    portoModel(
                        it.id_worker.orEmpty(),
                        it.name_aplication.orEmpty(),
                        it.link_repository.orEmpty(),
                        it.type_repository.orEmpty(),
                        it.type_portofolio.orEmpty(),
                        it.image.orEmpty()
                    )
                } ?: listOf()

                (binding.recyclerView.adapter as portoAdabter).addList(list)
            } else if (response is Throwable) {
                Log.e("android1", response.message ?: "Error")
            }
            binding.progressBar.visibility = View.GONE
            Log.d("android1", "finish : ${Thread.currentThread().name}")
        }
    }
}