package com.example.nanuer


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.nanuer.databinding.ActivityFindBinding
import com.google.android.material.tabs.TabLayoutMediator


class FindActivity : AppCompatActivity() {
    lateinit var binding: ActivityFindBinding
    //var findbtn : Button?=null


    private val tabTextList = arrayListOf("아이디 찾기", "비밀번호 찾기")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.findDeleteLogoIv.setOnClickListener {
            finish()
        }
        val findAdapter = FindVPAdapter(this)

        binding.findVp.adapter = findAdapter
        binding.findVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.findTb, binding.findVp) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()

   //     val findbtn: Button = findViewById<Button>(R.id.find_id_step1_find_btn)

//        findbtn.setOnClickListener {
//            findId()
//        }




//<<<<<<< HEAD
}
//    private fun findId(){
//
//        val phonenumber = findViewById<EditText>(R.id.find_id_step1_phone_number_et)
//        val phoneNumber : String = phonenumber.text.toString()
//
//        val authService = AuthService()
//        authService.setFindIdView(this)
//        authService.findId(User(phoneNumber))
//    }
//
//    override fun onFindIdSuccess(code:Int, result: FindIdResult) {
//        Log.d("onFindIdSuccess", "!!!!!")
//
//    }
//
//    override fun onFindIdFailure(code:Int, msg:String) {
//        Log.d("onFindIdFailure", "!!!!!")
//    }




//=======
//>>>>>>> ed5ed1561db501285dcad41e7257789819fc2f54
}

