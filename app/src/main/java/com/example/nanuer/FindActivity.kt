package com.example.nanuer


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.nanuer.databinding.ActivityFindBinding
import com.example.nanuer.databinding.ActivityLoginBinding
import com.google.android.material.tabs.TabLayoutMediator

private const val NUM_PAGES = 2

class FindActivity : AppCompatActivity() {

    lateinit var binding: ActivityFindBinding

    private lateinit var viewPager: ViewPager2
    val tabTextList = arrayListOf("아이디 찾기", "비밀번호 찾기")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.findVp.adapter = ScreenSlidePagerAdapter(this)
        binding.findVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.findTb, binding.findVp) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> FindIDFragment()
                else -> FindPWFragment()
            }
        }
    }




}
