package com.example.enggineraplication.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.enggineraplication.R
import com.example.enggineraplication.databinding.ItemWorkerBinding
import com.squareup.picasso.Picasso

class searchAdabter(val items: ArrayList<searchModel>
                    , val listener: OnClickViewListener
) : RecyclerView.Adapter<searchAdabter.searchHolder>() {

//    private var items = mutableListOf<workerModel>()

    fun addList(list: List<searchModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):searchHolder {
        return searchHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_worker, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: searchHolder, position: Int) {


        val item = items[position]
        Picasso.get().load("http://35.172.182.122:8080/uploads/"+item.image).into(holder.binding.image)
        holder.binding.tvName.text = item.name
        holder.binding.tvSkill.text = item.skill
        holder.binding.tvDomicile.text = item.domicile
        holder.binding.containerworker.setOnClickListener {
        listener.OnClick(item.id_worker)
        }
    }

    class searchHolder( val binding: ItemWorkerBinding) : RecyclerView.ViewHolder(binding.root)

//fun updatelist(list: MutableList<workerModel>){
//    items = list
//    notifyDataSetChanged()
//}
//
    interface OnClickViewListener{
        fun OnClick(id:String)
    }
}

