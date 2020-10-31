package com.example.enggineraplication.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.enggineraplication.*
import com.example.enggineraplication.databinding.ActivityLoginBinding
import com.example.enggineraplication.postprofile.postProfileActivity
import com.example.enggineraplication.regis.RegisActivity
import kotlinx.coroutines.*

class loginActivity : BaseActivity() {


    private lateinit var binding: ActivityLoginBinding
    lateinit var sharedPref: PreferenceHelper
    private lateinit var coroutineScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        sharedPref = PreferenceHelper(this)
        initlisteners()

    }


    override fun initlisteners() {
        binding.signup.setOnClickListener {
            val intentregis = Intent(this, RegisActivity::class.java)
            startActivity(intentregis)
        }
        binding.login.setOnClickListener {
            callSignInApi()

        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(Constant.pref_is_login)) {
            moveIntent()
        }
    }

    private fun moveIntent(){

        startActivity(Intent(this,postProfileActivity::class.java))
        finish()
    }



    private fun callSignInApi() {
        binding.loading.visibility = View.VISIBLE
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            Log.d("test", "login = ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("test", "call API = ${Thread.currentThread().name}")

                try {
                    service?.loginRequest(
                        binding.username.text.toString(),
                        binding.password.text.toString()
                    )
                } catch (e: Throwable) {
                    Log.e("onError", "onError : " + e.message);
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        Toast.makeText(applicationContext,"invalid username/password", Toast.LENGTH_SHORT).show()
                        binding.loading.visibility = View.GONE
                    }
                }
            }
            Log.d("test",response.toString())
            if (response is LoginResponse) {
                binding.loading.visibility = View.GONE

                if (response.success) {
                    sharedPref.put(Constant.PREF_TOKEN, response.data?.token.toString())
                    sharedPref.put(Constant.pref_is_login, true)
                    sharedPref.put(Constant.PREF_ID, response.data?.id.toString())
                    Log.e("ondata", response.data?.id.toString());
                    Toast.makeText(this@loginActivity, response.message, Toast.LENGTH_SHORT).show()
                    moveIntent()

                } else {
                    setErrorDialog("Error Login!", response.message)
                }

            }
        }
    }

    override fun onDestroy() {
        if (!sharedPref.getBoolean(Constant.pref_is_login)) coroutineScope.cancel()
        super.onDestroy()
    }

}
