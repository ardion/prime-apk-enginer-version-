package com.example.enggineraplication.addskill


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.enggineraplication.Constant
import com.example.enggineraplication.PreferenceHelper
import com.example.enggineraplication.R

import com.example.enggineraplication.databinding.ActivityAddSkillBinding

import com.example.enggineraplication.login.ApiClient
import com.example.enggineraplication.parentActivity

import com.example.enggineraplication.profile.skillprofile.skillApiService



class addSkillActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSkillBinding

    private lateinit var viewModel: AddSkillViewModel
    lateinit var sharedPref: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_skill)
        sharedPref= PreferenceHelper(this)
        val service = ApiClient.getApiClient(this)?.create(skillApiService::class.java)

        viewModel = ViewModelProvider(this).get(AddSkillViewModel::class.java)

        if (service != null) {
            viewModel.setLoginService(service)
        }

        binding.btnsubmit.setOnClickListener {
            sharedPref.getString(Constant.PREF_ID)?.let { it1 -> viewModel.postSKillApi(it1, binding.etSkilladd.text.toString()) }
            startActivity(Intent(this,parentActivity::class.java))
        }

        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        viewModel.isLoginLiveData.observe(this, Observer {
            Log.d("android1", "$it")
            if (it) {
                Toast.makeText(this, "Add Succcess", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Add Failed!", Toast.LENGTH_SHORT).show()
            }
        })

    }
}