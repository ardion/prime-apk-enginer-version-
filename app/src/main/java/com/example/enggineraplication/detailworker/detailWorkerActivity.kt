package com.example.enggineraplication.detailworker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.enggineraplication.R
import com.example.enggineraplication.databinding.ActivityDetailWorkerBinding
import com.example.enggineraplication.experience.experienceActivity
import com.example.enggineraplication.home.homeFragment
import com.example.enggineraplication.login.ApiClient
import com.example.enggineraplication.portofolio.portofolioActivity
import com.example.enggineraplication.profile.skillprofile.skillAdabter
import com.example.enggineraplication.profile.skillprofile.skillApiService
import com.example.enggineraplication.profile.skillprofile.skillModel
import com.example.enggineraplication.profile.skillprofile.skillResponse
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class detailWorkerActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailWorkerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_worker)
        binding.recyclerskilld.adapter = skillAdabter()
        binding.recyclerskilld.layoutManager = GridLayoutManager(this, 3)
        useCoroutineToCallAPI()
        binding.portofolio.setOnClickListener {
            val intent = Intent(this, portofolioActivity::class.java)
            startActivity(intent)
        }

        binding.exprience.setOnClickListener {
            val intent = Intent(this, experienceActivity::class.java)
            startActivity(intent)
        }

        skillAPI()


    }


    private fun useCoroutineToCallAPI() {

        val service = ApiClient.getApiClient(this)?.create(detailworkerapiservice::class.java)

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    service?.getAllWorker(intent.getStringExtra(homeFragment.ID_WORKER))
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is detailworkerResponse) {
                Log.d("android1", response.data.toString())
                binding.tvNamedetail.text = response.data?.name
                binding.etJobdesk.text = response.data?.jobdesk
                binding.etDomicile.text = response.data?.domicile
                binding.etDescworker.text = response.data?.description_personal
                binding.etInstagram.text = response.data?.instagram
                binding.etGithub.text = response.data?.github
                binding.etGitlab.text = response.data?.gitlab

                Picasso.get().load("http://35.172.182.122:8080/uploads/" + response.data?.image)
                    .into(binding.imageView)
            } else if (response is Throwable) {
                Log.e("android1", response.message ?: "Error")
            }
        }


    }

    private fun skillAPI() {

        val service = ApiClient.getApiClient(this)?.create(skillApiService::class.java)

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    service?.getAllSkill(intent.getStringExtra(homeFragment.ID_WORKER))
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is skillResponse) {
                Log.d("androskil", response.data.toString())
                Toast.makeText(this@detailWorkerActivity, "usecorotine", Toast.LENGTH_SHORT).show()
                val list = response.data?.map {
                    skillModel(
                        it.skill.orEmpty(),
                        it.id_skill.orEmpty()
                    )
                } ?: listOf()
                Log.d("hhhh", list.toString())

                (binding.recyclerskilld.adapter as skillAdabter).addList(list)

            } else if (response is Throwable) {
                Log.e("android1", response.message ?: "Error")
            }
        }
    }
}
