package com.example.enggineraplication.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.enggineraplication.Constant
import com.example.enggineraplication.PreferenceHelper
import com.example.enggineraplication.addskill.addSkillActivity
import com.example.enggineraplication.databinding.FragmentProfileBinding
import com.example.enggineraplication.detailworker.detailworkerResponse
import com.example.enggineraplication.detailworker.detailworkerapiservice
import com.example.enggineraplication.experience.experienceAdabter
import com.example.enggineraplication.home.homeaAdabter2
import com.example.enggineraplication.login.ApiClient
import com.example.enggineraplication.profile.skillprofile.skillAdabter
import com.example.enggineraplication.profile.skillprofile.skillApiService
import com.example.enggineraplication.profile.skillprofile.skillModel
import com.example.enggineraplication.profile.skillprofile.skillResponse
import com.example.enggineraplication.updateprofile.updateProfileActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class profileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var sharedPref: PreferenceHelper
    private lateinit var recyclerSkill : skillAdabter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        recyclerSkill = skillAdabter()
        binding= FragmentProfileBinding.inflate(inflater)

        binding.recyclerskill.adapter = skillAdabter()
//        binding.recyclerskill.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.recyclerskill.layoutManager = GridLayoutManager(requireContext(),3)
        sharedPref= context?.let { PreferenceHelper(it) }!!

 binding.etEmail.text=sharedPref.getString(Constant.EMAIL)

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
        binding.addskill.setOnClickListener {
//            startActivity(Intent(context, addSkillActivity::class.java))
            startActivityForResult(Intent(context, addSkillActivity::class.java), addSkillActivity.ADD_WORD_REQUEST_CODE)
        }
        return binding.root

    }

    private fun useCoroutineToCallAPI() {

        val service= context?.let { ApiClient.getApiClient(it)?.create(detailworkerapiservice::class.java) }

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    service?.getAllWorker(sharedPref.getString(Constant.PREF_IDWORKERP))
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
//            binding.progressBar.visibility = View.GONE
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
                    Log.d("idppppppp",sharedPref.getString(Constant.PREF_IDWORKERP).toString())
                    service?.getAllSkill(sharedPref.getString(Constant.PREF_IDWORKERP))
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is skillResponse) {
                Log.d("androskil", response.data.toString())
                Toast.makeText(context,"usecorotine", Toast.LENGTH_SHORT).show()
//                Log.d("android1", response.data.toString())
                val list = response.data?.map {
                    skillModel(
                        it.skill.orEmpty(),
                        it.id_skill.orEmpty()
                    )
                } ?: listOf()
                Log.d("hhhh", list.toString())

                (binding.recyclerskill.adapter as skillAdabter).addList(list)


            } else if (response is Throwable) {
                Log.e("android1", response.message ?: "Error")
            }

            binding.progressBar.visibility = View.GONE
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == addSkillActivity.ADD_WORD_REQUEST_CODE ) {

            binding.recyclerskill.adapter = skillAdabter()
//        binding.recyclerskill.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            binding.recyclerskill.layoutManager = GridLayoutManager(requireContext(),3)
            useCoroutineToCallAPI()
            skillAPI()

        }
    }

}