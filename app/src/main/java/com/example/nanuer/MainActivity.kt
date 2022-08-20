package com.example.nanuer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nanuer.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    val university = intent.getStringExtra("university")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }


    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fl, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl, HomeFragment().apply {
                            arguments = Bundle().apply {
                                putString("university", university)
                            }
                        })
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.postFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl, ListFragment().apply {
                            arguments = Bundle().apply {
                                putString("university", university)
                            }
                        })
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.chatFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl, ChatFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.mypageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl, MypageFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.makePostActivity -> {
                    startActivity(Intent(this,MakePostActivity::class.java))
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

}