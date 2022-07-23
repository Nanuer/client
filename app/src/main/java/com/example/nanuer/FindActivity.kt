package com.example.nanuer


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.nanuer.databinding.ActivityFindBinding
import com.google.android.material.tabs.TabLayoutMediator

class FindActivity : AppCompatActivity() {
    lateinit var binding: ActivityFindBinding

    private lateinit var viewPager: ViewPager2
    private val tabTextList = arrayListOf("아이디 찾기", "비밀번호 찾기")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val findAdapter = FindVPAdapter(this)

        binding.findVp.adapter = findAdapter
        binding.findVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.findTb, binding.findVp) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }
}
