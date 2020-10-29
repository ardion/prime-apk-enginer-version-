package com.example.enggineraplication.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.enggineraplication.Constant
import com.example.enggineraplication.PreferenceHelper
import com.example.enggineraplication.databinding.FragmentHomeBinding
import com.example.enggineraplication.detailworker.detailWorkerActivity
import com.example.enggineraplication.login.ApiClient
import com.example.enggineraplication.notificationActivity
import com.example.enggineraplication.parentActivity
import kotlinx.coroutines.*


class homeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var sharedPref: PreferenceHelper
    private lateinit var recyclerViewAndroid : homeaAdabter
    private lateinit var recyclerViewWeb : homeaAdabter2

    private lateinit var coroutineScope: CoroutineScope

    companion object {
        const val ID_WORKER = "anjay"
    }

//    private lateinit var RecycleWorker: homeaAdabter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater)
        sharedPref = context?.let { PreferenceHelper(it) }!!
//        recyclerViewAndroid= homeaAdabter()
//        recyclerViewWeb=homeaAdabter2()
//        binding.recyclerView.adapter = recyclerViewAndroid
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

//        binding.recyclerView2.adapter = recyclerViewWeb
        binding.recyclerView2.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        binding.icon.setOnClickListener{
            startActivity(Intent(context, notificationActivity::class.java))
        }


        useCoroutineToCallAPI()
        useCoroutineToCallAPI2()


        setUpRecyclerView()

        return binding.root

    }


    private fun useCoroutineToCallAPI2() {
//        binding.progressBar.visibility = View.VISIBLE
        val service2 =
            context?.let { ApiClient.getApiClient(it)?.create(homeapiservice2::class.java) }


        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {

            binding.progressBar.visibility = View.VISIBLE
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response2 = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    service2?.getAllWorker2()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response2 is homeresponse2) {
                Log.d("android1", response2.data.toString())
                val list = response2.data?.map {
                    workerModel2(
                        it.id_worker.orEmpty(),
                        it.name.orEmpty(),
                        it.image.orEmpty(),
                        it.domicile.orEmpty(),
                        it.skill.orEmpty()
                    )
                } ?: listOf()
                Log.d("android2", list.toString())
                (binding.recyclerView2.adapter as homeaAdabter2).addList(list)

            } else if (response2 is Throwable) {
                Log.e("android1", response2.message ?: "Error")
            }
            binding.progressBar.visibility = View.GONE
            Log.d("android1", "finish : ${Thread.currentThread().name}")
        }


    }




    private fun useCoroutineToCallAPI() {
//        binding.progressBar.visibility = View.VISIBLE
        val service =
            context?.let { ApiClient.getApiClient(it)?.create(homeapiservice::class.java) }

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {

            binding.progressBar.visibility = View.VISIBLE
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    service?.getAllWorker()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }


            if (response is homeresponse) {
                Log.d("android1", response.data.toString())
                val list = response.data?.map {
                    workerModel(
                        it.id_worker.orEmpty(),
                        it.name.orEmpty(),
                        it.image.orEmpty(),
                        it.domicile.orEmpty(),
                        it.skill.orEmpty()
                    )
                } ?: listOf()
                Log.d("android2", list.toString())
                (binding.recyclerView.adapter as homeaAdabter).addList(list)

            } else if (response is Throwable) {
                Log.e("android1", response.message ?: "Error")
            }
            binding.progressBar.visibility = View.GONE
            Log.d("android1", "finish : ${Thread.currentThread().name}")
        }


    }


    private fun setUpRecyclerView() {
        recyclerViewAndroid = homeaAdabter(arrayListOf(), object : homeaAdabter.OnClickViewListener {
            override fun OnClick(id: String) {
                Toast.makeText(activity, id, Toast.LENGTH_SHORT).show()
                sharedPref.put(Constant.PREF_IDWORKER, id)
                val intent = Intent(activity as AppCompatActivity, detailWorkerActivity::class.java)
                intent.putExtra(ID_WORKER, id)
                startActivity(intent)

            }
        })
        binding.recyclerView.adapter = recyclerViewAndroid
        binding.recyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        recyclerViewWeb = homeaAdabter2(arrayListOf(), object : homeaAdabter2.OnClickViewListener {
            override fun OnClick(id: String) {
                Toast.makeText(activity, id, Toast.LENGTH_SHORT).show()
                sharedPref.put(Constant.PREF_IDWORKER, id)
                val intent = Intent(activity as AppCompatActivity, detailWorkerActivity::class.java)
                intent.putExtra(ID_WORKER, id)
                startActivity(intent)
            }
        })
        binding.recyclerView2.adapter = recyclerViewWeb
        binding.recyclerView2.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)


    }



}
