package com.nugrahanggara.submissiondicoding.optionmenu

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.nugrahanggara.submissiondicoding.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("setting",Context.MODE_PRIVATE)

        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            when(isChecked){
                true ->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("keyDarkTheme",true)
                    editor.apply()
                    onStart()
                }
                false ->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("keyDarkTheme",false)
                    editor.apply()
                    onStart()
                }
            }
        }

        when(sharedPreferences.getBoolean("keyDarkTheme",false))
        {
            true -> {
                binding.switch1.isChecked = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            false -> {
                binding.switch1.isChecked = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }


}