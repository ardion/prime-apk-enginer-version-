package com.example.enggineraplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.enggineraplication.databinding.ActivityOnboardBinding
import com.example.enggineraplication.databinding.ActivityRegisBinding
import com.example.enggineraplication.login.loginActivity

class onboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_onboard
        )

        binding.loginworker.setOnClickListener {
            startActivity(Intent(this, loginActivity::class.java))
        }
    }
}