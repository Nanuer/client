package com.example.nanuer


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.example.nanuer.databinding.ActivityFindBinding
import com.example.nanuer.databinding.FragmentFindIdStep1Binding
import com.google.android.material.tabs.TabLayoutMediator

class FindActivity : AppCompatActivity(){
    lateinit var binding: ActivityFindBinding

    private lateinit var viewPager: ViewPager2
    private val tabTextList = arrayListOf("아이디 찾기", "비밀번호 찾기")


    var btn : Button? = null
    var sendbtn : Button? = null
    var resendbtn : Button? = null
    var check =true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickbtn()

        binding.findDeleteLogoIv.setOnClickListener{
            finish()
        }

        val findAdapter = FindVPAdapter(this)

        binding.findVp.adapter = findAdapter
        binding.findVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.findTb, binding.findVp) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()

        btn = findViewById<Button>(R.id.find_id_step1_send_number_btn)

    }

    fun  clickbtn(){
        btn?.setOnClickListener{onClick(btn)}
    }



      fun onClick(v: View?){
        when(v?.id) {
            R.id.find_id_step1_send_number_btn -> {
                val tView = findViewById<TextView>(R.id.find_id_step1_send_number_btn)
                if (check) {
                    tView.setText("인증번호")
                    check = false
                }
                else{
                    tView.setText("재전송")
                    tView.setTextColor(ContextCompat.getColor(this,R.color.blue_light_1))
                    check = true
                }
            }
        }

    }





}

