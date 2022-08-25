package com.example.nanuer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
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

    override fun onBackPressed() {
        handleAlertDialog()
    }

    private fun handleAlertDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_alert, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        val mAlertDialog = mBuilder.show()

        val okBtn = mDialogView.findViewById<TextView>(R.id.dialog_alert_ok_tv)
        val notOkBtn = mDialogView.findViewById<TextView>(R.id.dialog_alert_nok_tv)

        okBtn.setOnClickListener {
            ActivityCompat.finishAffinity(this)
            mAlertDialog.dismiss()
        }
        notOkBtn.setOnClickListener {
            mAlertDialog.dismiss()
        }
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