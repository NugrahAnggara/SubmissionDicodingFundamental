package com.nugrahanggara.submissiondicoding

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nugrahanggara.submissiondicoding.Adapter.SectionPagerAdapter
import com.nugrahanggara.submissiondicoding.Helper.DatabaseHelper
import com.nugrahanggara.submissiondicoding.Model.ItemsItem
import com.nugrahanggara.submissiondicoding.Model.ModelUserResponse
import com.nugrahanggara.submissiondicoding.Retrofit.ApiConfig
import com.nugrahanggara.submissiondicoding.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity()
{
    private lateinit var binding : ActivityDetailBinding
    private lateinit var viewPager : ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var imageView: ImageView
    private lateinit var username : TextView
    private lateinit var jmlhFollowers : TextView
    private lateinit var jmlhFollowing : TextView
    private lateinit var bio : TextView
    private lateinit var viewNama : TextView
    private var database : DatabaseHelper = DatabaseHelper(this@DetailActivity)
    private lateinit var sharedPreferences:SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharePreferencesSetting()

        val data = intent.getParcelableExtra<ItemsItem>(KEYDATA)

        var isDataExist = checkIfDataExist(data?.login)
        if (isDataExist == true){
            binding.toogle.isChecked = true
            binding.toogle.setOnClickListener {
                removeFromFavorite(data?.login)
            }
        }else{
            binding.toogle.isChecked = false
            binding.toogle.setOnClickListener {
                addToFavorite(data)
            }
        }


        initialisation()

        if (data != null){
            setProfile(data.login)
        }

        viewPager(data?.login)

    }

    private fun sharePreferencesSetting(){
        sharedPreferences = getSharedPreferences("setting", Context.MODE_PRIVATE)
        when(sharedPreferences.getBoolean("keyDarkTheme",false)){
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun checkIfDataExist(login: String?): Boolean {
        var condition = false
        val rows: ArrayList<HashMap<String, String>> = database.readData()
        for (i in 0 until rows.size) {
            val login = rows[i].get("nama")
            val avatar = rows[i].get("avatar")
            val followers = rows[i].get("followers")
            val following = rows[i].get("following")
            val bio = rows[i].get("bio")
            val name = rows[i].get("name")
            val data = ModelUserResponse(bio,login,followers?.toInt(),avatar,following?.toInt(),name)
            if (login == data.login){
                condition = true
            }else{
                condition = false
            }
        }

        return condition
    }

    private fun removeFromFavorite(login : String?){
        database.deleteBaseLogin(login!!)
    }

    private fun addToFavorite(data : ItemsItem?){
        database.insert(data!!)
    }

    private fun initialisation()
    {
        viewPager = binding.viewPager
        tabLayout = binding.tabs
        imageView = binding.ivDetail
        username = binding.textView
        jmlhFollowers = binding.tvJmlhFollower
        jmlhFollowing = binding.tvJmlhFollowing
        bio = binding.bio
        viewNama = binding.nama
    }

    private fun setProfile(data : String?)
    {
        val client = ApiConfig.getApiService().getDataUser(data!!)
        client.enqueue(object : Callback<ModelUserResponse>{
            override fun onResponse(
                call: Call<ModelUserResponse>,
                response: Response<ModelUserResponse>,
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    Glide.with(imageView)
                        .load(data?.avatarUrl)
                        .centerCrop()
                        .into(imageView)

                    username.text = data?.login
                    jmlhFollowers.text = "Followers : ${data?.followers}"
                    jmlhFollowing.text = "Following : ${data?.following}"
                    viewNama.text = data?.name
                    bio.text = data?.bio
                }
            }

            override fun onFailure(call: Call<ModelUserResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun viewPager(data: String?)
    {
        val sectionPagerAdapter = SectionPagerAdapter(this@DetailActivity,data)
        viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(tabLayout,viewPager){tab,position -> tab.text = resources.getString(TAB_TITLES[position]) }.attach()
    }


    companion object
    {
        const val KEYDATA = "data"
        private val TAB_TITLES = intArrayOf(R.string.text1,R.string.text2)
    }

}