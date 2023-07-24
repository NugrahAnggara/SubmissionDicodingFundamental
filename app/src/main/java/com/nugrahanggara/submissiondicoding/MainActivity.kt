package com.nugrahanggara.submissiondicoding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nugrahanggara.submissiondicoding.Adapter.AdapterSearch
import com.nugrahanggara.submissiondicoding.Model.ItemsItem
import com.nugrahanggara.submissiondicoding.Model.ModelUserResponse
import com.nugrahanggara.submissiondicoding.Retrofit.ApiConfig
import com.nugrahanggara.submissiondicoding.databinding.ActivityMainBinding
import com.nugrahanggara.submissiondicoding.optionmenu.FavoriteActivity
import com.nugrahanggara.submissiondicoding.optionmenu.SettingActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var list: ArrayList<ItemsItem>
    private lateinit var adapter: AdapterSearch
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingPreferences()
        list = arrayListOf()



        initialisation()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        recyclerView()
    }

    private fun settingPreferences()
    {
        sharedPreferences = getSharedPreferences("setting", Context.MODE_PRIVATE)
        when(sharedPreferences.getBoolean("keyDarkTheme",false))
        {
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun initialisation()
    {
        searchView = binding.searchView
        recyclerView = binding.rvSearch
        list = ArrayList()
    }

    private fun searchUser(query: String)
    {
        val search = query.replace(" ","")
        val client = ApiConfig.getApiService().getUserByQuery(search)
        client.enqueue(object : Callback<com.nugrahanggara.submissiondicoding.Model.Response>{
            override fun onResponse(
                call: Call<com.nugrahanggara.submissiondicoding.Model.Response>,
                response: Response<com.nugrahanggara.submissiondicoding.Model.Response>,
            ) {
                if (response.isSuccessful){
                    list.clear()
                    binding.loading.visibility = View.GONE
                    val data = response.body()?.items
                    data?.forEach {
                        val nama = it?.login
                        val avatar = it?.avatarUrl
                        val items = ItemsItem(nama,avatar)
                        list.add(items)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(
                call: Call<com.nugrahanggara.submissiondicoding.Model.Response>,
                t: Throwable,
            ) {
                Toast.makeText(this@MainActivity,"Data Gagal Didapatkan",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun recyclerView()
    {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdapterSearch(list, object : AdapterSearch.setOnClick {
            override fun onClick(item: ItemsItem) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(KEYDATA, item)
                startActivity(intent)
            }
        })

        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            R.id.favorite -> {startActivity(Intent(this@MainActivity,FavoriteActivity::class.java))}
            R.id.setting -> {startActivity(Intent(this@MainActivity,SettingActivity::class.java))}
        }
        return super.onOptionsItemSelected(item)
    }

    companion object
    {
        const val KEYDATA = "data"

    }
}