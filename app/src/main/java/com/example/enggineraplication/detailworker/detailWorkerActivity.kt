package com.example.enggineraplication.detailworker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.enggineraplication.R
import com.example.enggineraplication.databinding.ActivityDetailWorkerBinding
import com.example.enggineraplication.experience.experienceActivity
import com.example.enggineraplication.home.homeFragment
import com.example.enggineraplication.login.ApiClient
import com.example.enggineraplication.portofolio.portofolioActivity
import com.example.enggineraplication.profile.skillApiService
import com.example.enggineraplication.profile.skillResponse
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class detailWorkerActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailWorkerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_worker)
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
//    val retrofit = Retrofit.Builder()
//        .baseUrl("http://dummy.restapiexample.com/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
        val service= ApiClient.getApiClient(this)?.create(detailworkerapiservice::class.java)

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {
//            binding.progressBar.visibility = View.VISIBLE
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
                binding.tvNamedetail.text=response.data?.name
                binding.etJobdesk.text=response.data?.jobdesk
                binding.etDomicile.text=response.data?. domicile
                binding.etDescworker.text=response.data?.description_personal
                binding.etInstagram.text=response.data?.instagram
                binding.etGithub.text=response.data?.github
                binding.etGitlab.text=response.data?.gitlab

                Picasso.get().load("http://35.172.182.122:8080/uploads/"+response.data?.image).into(binding.imageView)




            } else if (response is Throwable) {
                Log.e("android1", response.message ?: "Error")
            }
        }


    }

    private fun skillAPI() {

        val service=  ApiClient.getApiClient(this)?.create(skillApiService::class.java)

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {
//            binding.progressBar.visibility = View.VISIBLE
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
                binding.etSkill.text=response.data?.skill.toString()


            } else if (response is Throwable) {
                Log.e("android1", response.message ?: "Error")
            }
        }


    }

}
