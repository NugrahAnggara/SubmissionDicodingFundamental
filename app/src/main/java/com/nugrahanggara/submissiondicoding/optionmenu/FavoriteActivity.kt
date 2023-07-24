package com.nugrahanggara.submissiondicoding.optionmenu

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nugrahanggara.submissiondicoding.Adapter.AdapterFavorite
import com.nugrahanggara.submissiondicoding.DetailActivity
import com.nugrahanggara.submissiondicoding.Helper.DatabaseHelper
import com.nugrahanggara.submissiondicoding.Model.ItemsItem
import com.nugrahanggara.submissiondicoding.Model.ModelUserResponse
import com.nugrahanggara.submissiondicoding.databinding.ActivityFavoriteBinding


class FavoriteActivity : AppCompatActivity()
{
    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var recyclerview : RecyclerView
    private lateinit var adapterFavorite : AdapterFavorite
    private var list = ArrayList<ItemsItem>()
    private lateinit var db : DatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingPreference()
        initialisation()
        readData()
        settingRecyclerView()
    }

    private fun settingPreference(){
        sharedPreferences = getSharedPreferences("setting",Context.MODE_PRIVATE)
        when(sharedPreferences.getBoolean("keyDarkTheme",false))
        {
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun initialisation(){
        adapterFavorite = AdapterFavorite(list,object : AdapterFavorite.onClick{
            override fun clicker(data: ItemsItem) {
                val intent = Intent(this@FavoriteActivity,DetailActivity::class.java)
                intent.putExtra("data",data)
                startActivity(intent)
            }
        })
        db = DatabaseHelper(this@FavoriteActivity)
        recyclerview = binding.rvFavorite
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun readData(){
        val rows: ArrayList<HashMap<String, String>> = db.readData()
        list.clear()
        for (i in 0 until rows.size) {
            val login = rows[i].get("nama")
            val avatar = rows[i].get("avatar")
            val data = ItemsItem(login,avatar)
            list.add(data)
        }
        adapterFavorite.notifyDataSetChanged()
    }

    private fun settingRecyclerView(){
        recyclerview.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        recyclerview.adapter = adapterFavorite
    }

    override fun onResume() {
        super.onResume()
        list.clear()
        readData()
        adapterFavorite.notifyDataSetChanged()
    }
}