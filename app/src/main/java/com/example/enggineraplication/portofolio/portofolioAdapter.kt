package com.example.enggineraplication.portofolio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.enggineraplication.R
import com.example.enggineraplication.databinding.ItemPortoBinding

import com.squareup.picasso.Picasso

class portoAdabter() : RecyclerView.Adapter<portoAdabter.portoHolder>() {

    private var items = mutableListOf<portoModel>()

    fun addList(list: List<portoModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): portoHolder {
        return portoHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_porto,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: portoHolder, position: Int) {
        val item = items[position]
        Picasso.get().load("http://35.172.182.122:8080/uploads/" + item.image)
            .into(holder.binding.tvImageporto)
        holder.binding.tvNameApp.text = item.name_aplication
        holder.binding.tvLinkrepo.text = item.link_repository
        holder.binding.tvTyperepo.text = item.type_repository
        holder.binding.tvTypeporto.text = item.type_portofolio
    }

    class portoHolder(val binding: ItemPortoBinding) : RecyclerView.ViewHolder(binding.root)


}
