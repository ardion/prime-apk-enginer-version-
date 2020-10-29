package com.example.enggineraplication.offers

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
import com.example.enggineraplication.databinding.FragmentNotifBinding
import com.example.enggineraplication.transaction.detailOffersActivity
import com.example.enggineraplication.login.ApiClient
import kotlinx.coroutines.*

class notifFragment : Fragment() {

    lateinit var binding: FragmentNotifBinding
    lateinit var sharedPref: PreferenceHelper
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var recyclerView: notifAdabter
    companion object {
        const val orderworker = "orderworker"
        const val namecompany = "namecompany"
        const val nameprojecti = "nameproject"
        const val pricei = "price"
        const val massagei = "massage"
        const val  jobi = "job"
        const val  statusi = "status"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotifBinding.inflate(inflater)
        sharedPref = context?.let { PreferenceHelper(it) }!!
//        binding.recyclerView.adapter = notifAdabter()
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        useCoroutineToCallAPI()
        setUpRecyclerView()
        return binding.root
    }


    private fun useCoroutineToCallAPI() {
//        binding.progressBar.visibility = View.VISIBLE
        val service =
            context?.let { ApiClient.getApiClient(it)?.create(notifapiservice::class.java) }
        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {

            binding.progressBar.visibility = View.VISIBLE
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    val a = sharedPref.getString(Constant.PREF_IDWORKER)
                    service?.getAllNotif("30")
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is notifResponse) {
                Log.d("android1", response.data.toString())
                val list = response.data?.map {
                    notifModel(
                        it.image.orEmpty(),
                        it.id_company.orEmpty(),
                        it.company_name.orEmpty(),
                        it.name_project.orEmpty(),
                        it.order_worker.orEmpty(),
                        it.id_project.orEmpty(),
                        it.message.orEmpty(),
                        it.price.orEmpty(),
                        it.project_job.orEmpty(),
                        it.status.orEmpty()
                    )
                } ?: listOf()


                (binding.recyclerView.adapter as notifAdabter).addList(list)
            } else if (response is Throwable) {
                Log.e("android1", response.message ?: "Error")
            }
            binding.progressBar.visibility = View.GONE
            Log.d("android1", "finish : ${Thread.currentThread().name}")
        }


    }

    private fun setUpRecyclerView() {
        recyclerView = notifAdabter(arrayListOf(), object : notifAdabter.OnClickViewListener {

            override fun OnClick(
                id: String,
                companyname: String,
                nameproject: String,
                massage: String,
                price: String,
                job: String,
                status: String
            ) {
//                Toast.makeText(activity, id, Toast.LENGTH_SHORT).show()
                Toast.makeText(activity, companyname, Toast.LENGTH_SHORT).show()
                val intent = Intent(activity as AppCompatActivity, detailOffersActivity::class.java)
                intent.putExtra(orderworker, id)
                intent.putExtra(namecompany, companyname)
                intent.putExtra(nameprojecti, nameproject)
                intent.putExtra(pricei, price)
                intent.putExtra(massagei, massage)
                intent.putExtra(jobi, job)
                intent.putExtra(statusi, status)
                startActivity(intent)

//                const val namecompany = "namecompany"
//                const val nameproject = "nameproject"
//                const val price = "price"
//                const val massage = "massage"
//                const val  job = "job"
//                const val  status = "status"
            }
        })
        binding.recyclerView.adapter = recyclerView
        binding.recyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

    }

}