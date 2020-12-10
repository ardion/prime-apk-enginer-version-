package com.example.enggineraplication.profile

import android.app.Activity
import android.content.Intent
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
import com.example.enggineraplication.databinding.ActivityPortoProfileBinding
import com.example.enggineraplication.databinding.ActivityPortofolioBinding
import com.example.enggineraplication.login.ApiClient
import com.example.enggineraplication.portofolio.portoAdabter
import com.example.enggineraplication.portofolio.portoModel
import com.example.enggineraplication.portofolio.portofolioApiService
import com.example.enggineraplication.portofolio.portoresponse
import kotlinx.coroutines.*

class portoProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityPortoProfileBinding
    lateinit var sharedPref: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_porto_profile)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_porto_profile)
        sharedPref = PreferenceHelper(this)
        useCoroutineToCallAPI()
        binding.recyclerView.adapter = portoAdabter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.btnAddPorto.setOnClickListener {
            startActivityForResult(
                Intent(this, addPortoActivity::class.java),
                addPortoActivity.ADD_WORD_REQUEST_CODE
            )
        }

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
                    service?.getAllPorto(sharedPref.getString(Constant.PREF_IDWORKERP))
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == addPortoActivity.ADD_WORD_REQUEST_CODE) {
            useCoroutineToCallAPI()
            binding.recyclerView.adapter = portoAdabter()
            binding.recyclerView.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        }
    }
}