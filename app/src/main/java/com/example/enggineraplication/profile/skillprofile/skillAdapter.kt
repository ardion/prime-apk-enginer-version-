package com.example.enggineraplication.profile.skillprofile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.enggineraplication.R
import com.example.enggineraplication.databinding.ItemWorkerBinding
import com.example.enggineraplication.databinding.ItemskillBinding
import com.example.enggineraplication.home.workerModel
import com.squareup.picasso.Picasso

class skillAdabter() : RecyclerView.Adapter<skillAdabter.skillHolder>() {

    private var items = mutableListOf<skillModel>()

    fun addList(list: List<skillModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):skillHolder {
        return skillHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.itemskill, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: skillHolder, position: Int) {
        val item = items[position]
        holder.binding.skill.text = item.skill
    }

    class skillHolder( val binding: ItemskillBinding) : RecyclerView.ViewHolder(binding.root)

}