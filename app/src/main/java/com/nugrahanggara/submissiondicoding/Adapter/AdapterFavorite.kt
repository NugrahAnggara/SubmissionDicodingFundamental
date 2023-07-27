package com.nugrahanggara.submissiondicoding.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nugrahanggara.submissiondicoding.Model.ItemsItem
import com.nugrahanggara.submissiondicoding.Model.ModelUserResponse
import com.nugrahanggara.submissiondicoding.R


class AdapterFavorite(private val list : List<ItemsItem>,private val listener : onClick) : RecyclerView.Adapter<AdapterFavorite.MyViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val data = list[position]

        holder.tv_favorite.text = data.login
        Glide.with(holder.iv_favorite)
            .load(data.avatarUrl)
            .centerCrop()
            .into(holder.iv_favorite)

        holder.itemView.setOnClickListener {
            listener.clicker(data)
        }
    }

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tv_favorite = view.findViewById<TextView>(R.id.tv_favorite)
        val iv_favorite = view.findViewById<ImageView>(R.id.iv_favorite)
    }
    interface onClick{
        fun clicker(data : ItemsItem)
    }
}
