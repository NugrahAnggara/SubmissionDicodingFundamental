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

class FollowingAdapter(private val listData : List<FollowersResponse>) : RecyclerView.Adapter<FollowingAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ): FollowingAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_following,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingAdapter.MyViewHolder, position: Int) {
        val data = listData[position]

        holder.tvFollowing.text = data.login
        Glide.with(holder.imageFollowing)
            .load(data.avatarUrl)
            .centerCrop()
            .into(holder.imageFollowing)
    }

    override fun getItemCount(): Int = listData.size

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvFollowing = view.findViewById<TextView>(R.id.tv_following)
        val imageFollowing = view.findViewById<ImageView>(R.id.iv_following)
    }
}