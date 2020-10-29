package com.example.enggineraplication.experience

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.enggineraplication.R
import com.example.enggineraplication.databinding.ItemExperienceBinding
import com.squareup.picasso.Picasso

class experienceAdabter() : RecyclerView.Adapter<experienceAdabter.experienceHolder>() {

    private var items = mutableListOf<experienceModel>()

    fun addList(list: List<experienceModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):experienceHolder {
        return experienceHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_experience, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: experienceHolder, position: Int) {


        val item = items[position]
        holder.binding.tvPosition.text = item.position
        holder.binding.tvNamecompany.text = item.company_name
        holder.binding.tvDescwork.text = item.description_work
        holder.binding.tvDate.text = item.date
    }

    class experienceHolder( val binding: ItemExperienceBinding) : RecyclerView.ViewHolder(binding.root)


}
