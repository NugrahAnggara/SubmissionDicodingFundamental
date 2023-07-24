package com.nugrahanggara.submissiondicoding.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nugrahanggara.submissiondicoding.Model.ItemsItem
import com.nugrahanggara.submissiondicoding.R

class AdapterSearch(private val data : List<ItemsItem>,private val listener : setOnClick) : RecyclerView.Adapter<AdapterSearch.MyViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val data = data[position]
        holder.username.text = data.login
        Glide.with(holder.imageUser)
            .load(data.avatarUrl)
            .centerCrop()
            .into(holder.imageUser)

        holder.itemView.setOnClickListener{
            listener.onClick(data)
        }
    }

    class MyViewHolder( view : View ) : RecyclerView.ViewHolder( view )
    {
        val username : TextView = view.findViewById(R.id.tv_username)
        val imageUser : ImageView = view.findViewById(R.id.iv_profileSearch)
    }

    interface setOnClick{
        fun onClick(item : ItemsItem)
    }
}