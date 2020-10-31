package com.example.enggineraplication.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.enggineraplication.PreferenceHelper
import com.example.enggineraplication.databinding.FragmentProfileBinding
import com.example.enggineraplication.detailworker.detailworkerResponse
import com.example.enggineraplication.detailworker.detailworkerapiservice
import com.example.enggineraplication.login.ApiClient
import com.example.enggineraplication.updateprofile.updateProfileActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class profileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var sharedPref: PreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentProfileBinding.inflate(inflater)
        sharedPref= context?.let { PreferenceHelper(it) }!!



        useCoroutineToCallAPI()
        skillAPI()
        binding.portofolio.setOnClickListener {
            startActivity(Intent(context, portoProfileActivity::class.java))
        }
        binding.exprience.setOnClickListener {
            startActivity(Intent(context, experienceProfileActivity::class.java))
        }
binding.updateprofile.setOnClickListener {
    startActivity(Intent(context, updateProfileActivity::class.java))
}
        return binding.root

    }

    private fun useCoroutineToCallAPI() {

        val service= context?.let { ApiClient.getApiClient(it)?.create(detailworkerapiservice::class.java) }

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {
//            binding.progressBar.visibility = View.VISIBLE
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    service?.getAllWorker("30")
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

        val service= context?.let { ApiClient.getApiClient(it)?.create(skillApiService::class.java) }

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {
//            binding.progressBar.visibility = View.VISIBLE
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    service?.getAllSkill("30")
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