package com.example.enggineraplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.enggineraplication.databinding.ActivityOnboardBinding
import com.example.enggineraplication.databinding.ActivityRegisBinding
import com.example.enggineraplication.login.loginActivity
import com.example.enggineraplication.postprofile.postProfileActivity

class onboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardBinding
    lateinit var sharedPref: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref= PreferenceHelper(this)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_onboard
        )

        binding.loginworker.setOnClickListener {
            startActivity(Intent(this, loginActivity::class.java))
        }
    }


    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(Constant.pref_is_login)) {
            startActivity(Intent(this,loginActivity::class.java))
        }
    }
}