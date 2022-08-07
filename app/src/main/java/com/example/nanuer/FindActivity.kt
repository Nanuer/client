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

        val findbtn = findViewById<Button>(R.id.find_id_step1_find_btn)

        findbtn.setOnClickListener {
            findId()
        }


}




}


