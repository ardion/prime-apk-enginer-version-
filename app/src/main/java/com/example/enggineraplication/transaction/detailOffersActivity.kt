package com.example.enggineraplication.transaction

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.enggineraplication.Constant
import com.example.enggineraplication.R
import com.example.enggineraplication.databinding.ActivityDetailOffersBinding
import com.example.enggineraplication.login.ApiClient
import com.example.enggineraplication.login.AuthApiService
import com.example.enggineraplication.login.LoginResponse
import com.example.enggineraplication.offers.notifFragment
import kotlinx.coroutines.*

class detailOffersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailOffersBinding


    companion object {

        const val ADD_WORD_REQUEST_CODE = 9013;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_detail_offers
        )
        var a = intent.getStringExtra(notifFragment.orderworker)
        Log.d("inten", a.toString())
        binding.tvNameproject.text = intent.getStringExtra(notifFragment.nameprojecti)
        binding.etCompanyname.text = intent.getStringExtra(notifFragment.namecompany)
        binding.etPrice.text = intent.getStringExtra(notifFragment.pricei)
        binding.etMassage.text = intent.getStringExtra(notifFragment.massagei)
        binding.etJobdesk.text = intent.getStringExtra(notifFragment.jobi)
        binding.acc.setOnClickListener {
            patch("accept")
        }

        binding.Reject.setOnClickListener {
            patch("reject")

        }
    }


    private fun patch(decision: String) {
        binding.progressBar.visibility = View.VISIBLE
        val service = ApiClient.getApiClient(this)?.create(transactionApiService::class.java)

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            Log.d("test", "login = ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("test", "call API = ${Thread.currentThread().name}")

                try {
                    service?.patchtransaction(
                        intent.getStringExtra(notifFragment.orderworker),
                        decision
                    )
                } catch (e: Throwable) {
                    Log.e("onError", "onError : " + e.message);
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "failed process", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            Log.d("test", response.toString())
            if (response is transactionResponse) {

            }
            binding.progressBar.visibility = View.GONE
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}