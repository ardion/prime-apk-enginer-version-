package com.example.enggineraplication.regis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.enggineraplication.*
import com.example.enggineraplication.databinding.ActivityRegisBinding
import com.example.enggineraplication.login.ApiClient
import com.example.enggineraplication.login.loginActivity
import kotlinx.coroutines.*

class RegisActivity : BaseActivity() {
    private lateinit var binding:ActivityRegisBinding
    lateinit var sharedPref: PreferenceHelper
    private lateinit var coroutineScope: CoroutineScope


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_regis
        )
        sharedPref = PreferenceHelper(this)
        initlisteners()

    }

    override fun initlisteners() {
        binding.SIGNUP.setOnClickListener {
            Log.d("pwcoba",binding.pw.text.toString())
            Log.d("emailcoba",binding.email.text.toString())
            callSignInApi()
        }
        binding.signin.setOnClickListener {
            moveIntent()
        }
    }
    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(Constant.pref_is_signup)) {
            moveIntent()
        }
    }

    private fun moveIntent(){
        startActivity(Intent(this, loginActivity::class.java))
        finish()
    }

    private fun saveSession(username:String,pasword:String){
        sharedPref.put(Constant.pref_is_regUsername, username)
        sharedPref.put(Constant.pref_is_regPasword, pasword)
        sharedPref.put(Constant.pref_is_signup, true)
    }

    private fun showmessage(message:String){
        Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
    }

    private fun callSignInApi() {
        binding.loading.visibility = View.VISIBLE
        val service = ApiClient.getApiClient(this)?.create(RegisApiService::class.java)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.registerRequest(
                        binding.username.text.toString(),
                        binding.email.text.toString(),
                        binding.pw.text.toString(),
                        binding.HP.text.toString()
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is RegisResponse) {
                binding.loading.visibility = View.GONE

                if (response.success) {
                    Log.d("register", "response $response")
                    val intent = Intent(this@RegisActivity, loginActivity::class.java)
                    intent.putExtra(Constant.EXTRA_EMAIL, "${binding.email.editableText}")
                    intent.putExtra(
                        Constant.EXTRA_PASSWORD,
                        "${binding.pw.editableText}"
                    )
                    saveSession(
                        binding.email.editableText.toString(),
                        binding.pw.editableText.toString()
                    )
                    sharedPref.put(
                        Constant.PREF_FULL_NAME,
                        binding.username.editableText.toString()
                    )
                    startActivity(intent)
                } else {
                    setErrorDialog("Error Register!", response.message)
                }

            }
        }
    }

//    override fun onDestroy() {
//        if (!sharedPref.getBoolean(Constant.pref_is_login)) coroutineScope.cancel()
//        super.onDestroy()
//    }

}

//    override fun onDestroy() {
//        if (!sharedPref.getBoolean(Constant.pref_is_login)) coroutineScop.cancel()
//        super.onDestroy()
//    }