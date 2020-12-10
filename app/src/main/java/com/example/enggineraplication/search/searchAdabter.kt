package com.example.enggineraplication.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.enggineraplication.R
import com.example.enggineraplication.databinding.ItemWorkerBinding
import com.squareup.picasso.Picasso

class searchAdabter(
    val items: ArrayList<searchModel>, val listener: OnClickViewListener
) : RecyclerView.Adapter<searchAdabter.searchHolder>() {


    fun addList(list: List<searchModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): searchHolder {
        return searchHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_worker,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: searchHolder, position: Int) {

        val item = items[position]
        Picasso.get().load("http://35.172.182.122:8080/uploads/" + item.image)
            .into(holder.binding.image)
        holder.binding.tvName.text = item.name

        var word = item.skill.split(",")
        var remains = word.size - 3
        Log.d("sklllll", remains.toString())

        if (word.size < 2) {
            holder.binding.tvSkill1.text = word[0]
            holder.binding.tvSkill2.visibility = View.GONE
            holder.binding.tvSkill3.visibility = View.GONE
            holder.binding.tvMin.visibility = View.GONE
            holder.binding.tvPlus.visibility = View.GONE

        } else if (word.size == 2) {
            holder.binding.tvSkill1.text = word[0]
            holder.binding.tvSkill2.text = word[1]
            holder.binding.tvSkill3.visibility = View.GONE
            holder.binding.tvMin.visibility = View.GONE
            holder.binding.tvPlus.visibility = View.GONE

        } else if (word.size > 2) {
            holder.binding.tvSkill1.text = word[0]
            holder.binding.tvSkill2.text = word[1]
            holder.binding.tvSkill3.text = word[2]
            holder.binding.tvMin.text = remains.toString()
        }
        holder.binding.tvDomicile.text = item.domicile
        holder.binding.containerworker.setOnClickListener {
            listener.OnClick(item.id_worker)
        }
    }

    class searchHolder(val binding: ItemWorkerBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnClickViewListener {
        fun OnClick(id: String)
    }
}

