package com.example.enggineraplication.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.enggineraplication.R
import com.example.enggineraplication.databinding.ItemWorkerBinding
import com.squareup.picasso.Picasso

class homeaAdabter2(val items: ArrayList<workerModel2>,val listener: homeaAdabter2.OnClickViewListener) : RecyclerView.Adapter<homeaAdabter2.homeHolder2>() {
    //    val items: ArrayList<workerModel>
//                   , val listener: OnClickViewListener

//    private var items = mutableListOf<workerModel2>()

    fun addList(list: List<workerModel2>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):homeHolder2 {
        return homeHolder2(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_worker, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: homeHolder2, position: Int) {
        val item = items[position]
        Picasso.get().load("http://35.172.182.122:8080/uploads/"+item.image).into(holder.binding.image)
        holder.binding.tvName.text = item.name
        holder.binding.tvSkill.text = item.skill
        holder.binding.tvDomicile.text = item.domicile
        holder.binding.containerworker.setOnClickListener {
            listener.OnClick(item.id_worker)
        }
    }

    class homeHolder2( val binding: ItemWorkerBinding) : RecyclerView.ViewHolder(binding.root)



//fun updatelist(list: MutableList<workerModel>){
//    items = list
//    notifyDataSetChanged()
//}
////
    interface OnClickViewListener{
        fun OnClick(id:String)
    }
}