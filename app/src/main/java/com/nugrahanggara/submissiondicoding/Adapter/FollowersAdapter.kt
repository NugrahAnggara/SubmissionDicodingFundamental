package com.nugrahanggara.submissiondicoding.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nugrahanggara.submissiondicoding.Model.FollowersResponse
import com.nugrahanggara.submissiondicoding.R


class FollowersAdapter(private val dataFollowers : List<FollowersResponse>) : RecyclerView.Adapter<FollowersAdapter.MyViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_followers,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = dataFollowers.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val data = dataFollowers[position]

        holder.usernameFollowers.text = data.login
        Glide.with(holder.photoFollower)
            .load(data.avatarUrl)
            .centerCrop()
            .into(holder.photoFollower)
    }

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val usernameFollowers : TextView = view.findViewById(R.id.tv_followers)
        val photoFollower : ImageView = view.findViewById(R.id.iv_followers)
    }
}
