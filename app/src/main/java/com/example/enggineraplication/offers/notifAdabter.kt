package com.example.enggineraplication.offers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.enggineraplication.R
import com.example.enggineraplication.databinding.ItemNotifBinding
import com.example.enggineraplication.home.homeaAdabter
import com.example.enggineraplication.home.homeaAdabter2
import com.example.enggineraplication.home.workerModel2
import com.squareup.picasso.Picasso

class notifAdabter(val items: ArrayList<notifModel>, val listener: notifAdabter.OnClickViewListener) : RecyclerView.Adapter<notifAdabter.notifHolder>() {

//    val items: ArrayList<notifModel>, val listener: OnClickViewListener
//    private var items = mutableListOf<notifModel>()


    fun addList(list: List<notifModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):notifHolder {
        return notifHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_notif, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: notifHolder, position: Int) {


        val item = items[position]
        Picasso.get().load("http://35.172.182.122:8080/uploads/"+item.image).into(holder.binding.image)
        holder.binding.tvCompayname.text = item.company_name
        holder.binding.tvNameproject.text = item.name_project
        holder.binding.tvStatusproject.text = item.status
        holder.binding.containerproject.setOnClickListener {
            listener.OnClick(item.order_worker,item.company_name,item.name_project, item.message, item.price, item.project_job, item.status)
        }

    }

    class notifHolder( val binding:ItemNotifBinding) : RecyclerView.ViewHolder(binding.root)

  interface OnClickViewListener{
        fun OnClick(id:String, companyname:String, nameproject:String, massage:String, price:String,job:String, status:String)
    }
}