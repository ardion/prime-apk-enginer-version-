package com.example.enggineraplication.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.enggineraplication.databinding.FragmentProfileBinding
import com.example.enggineraplication.databinding.FragmentSearchBinding
import com.example.enggineraplication.detailworker.detailWorkerActivity
import com.example.enggineraplication.login.ApiClient
import kotlinx.coroutines.*


class searchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var sharedPref: PreferenceHelper

    private lateinit var coroutineScope: CoroutineScope

    companion object {
        const val ID_WORKER = "anjay"
    }

    private lateinit var RecycleWorker: searchAdabter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater)
        sharedPref = context?.let { PreferenceHelper(it) }!!
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            private var searchFor = ""
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if (searchText == searchFor)
                    return

                searchFor = searchText
                val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
                coroutineScope.launch {
                    delay(300)  //debounce timeOut
                    if (searchText != searchFor)
                        return@launch

                    useCoroutineToCallAPI(searchFor)
                }
            }
        })

        setUpRecyclerView()

        return binding.root

    }


    private fun useCoroutineToCallAPI(search: String) {
        val service =
            context?.let { ApiClient.getApiClient(it)?.create(searchapiservice::class.java) }

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {

            binding.progressBar.visibility = View.VISIBLE
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    var a = binding.spiner.selectedItem.toString()
                    Log.d("koloomm", a)

                    if (a == "name") {
                        service?.getAllSearchname(100, search)
                    } else if (a == "skill") {
                        service?.getAllSearchSkill(100, search)
                    } else {
                        service?.getAllSearchDomicile(100, search)
                    }

                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is searchresponse) {
                Log.d("android1", response.data.toString())
                val list = response.data?.map {
                    searchModel(
                        it.id_worker.orEmpty(),
                        it.name.orEmpty(),
                        it.image.orEmpty(),
                        it.domicile.orEmpty(),
                        it.skill.orEmpty()
                    )
                } ?: listOf()
                Log.d("android2", list.toString())
                (binding.recyclerView.adapter as searchAdabter).addList(list)
            } else if (response is Throwable) {
                Log.e("android1", response.message ?: "Error")
            }
            binding.progressBar.visibility = View.GONE
            Log.d("android1", "finish : ${Thread.currentThread().name}")
        }


    }

    private fun setUpRecyclerView() {
        RecycleWorker = searchAdabter(arrayListOf(), object : searchAdabter.OnClickViewListener {
            override fun OnClick(id: String) {
                Toast.makeText(activity, id, Toast.LENGTH_SHORT).show()
                sharedPref.put(Constant.PREF_IDWORKER, id)
                val intent = Intent(activity as AppCompatActivity, detailWorkerActivity::class.java)
                intent.putExtra(ID_WORKER, id)
                startActivity(intent)
            }
        })
        binding.recyclerView.adapter = RecycleWorker
        binding.recyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

    }
}