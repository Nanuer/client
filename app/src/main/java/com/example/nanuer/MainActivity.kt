package com.example.nanuer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nanuer.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity(),GetUserInfoView {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUserInfo()
    }


    private fun initBottomNavigation(university:String, nickname:String){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fl, ListFragment().apply{
                arguments = Bundle().apply {
                    putString("university", university)
                }
            })
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl, HomeFragment().apply{
                            arguments = Bundle().apply {
                                putString("university", university)
                            }
                        })
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.postFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl, ListFragment().apply{
                            arguments = Bundle().apply {
                                putString("university", university)
                            }
                        })
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

//                R.id.chatFragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_fl, ChatFragment())
//                        .commitAllowingStateLoss()
//                    return@setOnItemSelectedListener true
//                }
                R.id.mypageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl, MypageFragment().apply{
                            arguments = Bundle().apply {
                                putString("nickname", nickname)
                            }
                        })
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
//                R.id.makePostActivity -> {
//                    startActivity(Intent(this,MakePostActivity::class.java))
//                    return@setOnItemSelectedListener true
//                }
            }
            false
        }
    }

    private fun getUserInfo(){
        val jwt = getJwt()
        val authService = AuthService()
        authService.setGetUserInfoView(this)
        authService.getUserInfo(jwt!!)
    }

    private fun getJwt():String?{
        val spf = this.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("jwt","0")
    }

    override fun onGetUserInfoSuccess(userInfo: User) {
        val university = userInfo.university
        val nickname = userInfo.nickName
        initBottomNavigation(university!!, nickname!!)
    }

    override fun onGetUserInfoFailure(code: Int, msg: String) {

    }

}