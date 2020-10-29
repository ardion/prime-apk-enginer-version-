package com.example.enggineraplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat

class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var handler= Handler()
        handler.postDelayed({
            val intentt = Intent(this, onboardActivity::class.java)
            startActivity(intentt)
            finish()
        }, 3000)
    }
}