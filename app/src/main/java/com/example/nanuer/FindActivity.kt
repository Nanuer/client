package com.example.nanuer


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.nanuer.databinding.ActivityFindBinding
import com.example.nanuer.databinding.FragmentFindIdStep1Binding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.concurrent.timer

class FindActivity : AppCompatActivity() {
    lateinit var binding: ActivityFindBinding

    private val tabTextList = arrayListOf("아이디 찾기", "비밀번호 찾기")


    var btn: Button? = null
    var btn2: Button? = null
    var check = true
    var check2 = true
    var time = 3
    var timerTask: Timer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickbtn()
        clickbtn2()

        binding.findDeleteLogoIv.setOnClickListener {
            finish()
        }

        val findAdapter = FindVPAdapter(this)

        binding.findVp.adapter = findAdapter
        binding.findVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.findTb, binding.findVp) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()

        btn = findViewById<Button>(R.id.find_id_step1_send_number_btn)
        btn2 = findViewById<Button>(R.id.find_id_step1_okay_btn)

    }

    fun starttimer() {

        val timecheck = findViewById<TextView>(R.id.find_id_step1_timer)

        timerTask = timer(period = 1000) {
            time--
            val min = time / 100
            val sec = time

            runOnUiThread {
                timecheck?.text= "${sec}"// TextView 세팅
            }
        }
    }

//    fun laptime() {
//
//        val lapTime = time
//        val lap_layout = findViewById<TextView>(R.id.find_id_step1_timer)
//
//        val textView = findViewById<TextView>(R.id.find_id_step1_timer).apply {
//            setTextSize(12f)
//
//        }
//
//        textView.text = "남은시간 ${lapTime / 100}.${lapTime % 100}"
//
//    }



    fun clickbtn() {
        btn?.setOnClickListener { onClick(btn) }
    }

    fun clickbtn2() {
        btn2?.setOnClickListener { onClick2(btn2) }
    }


    fun onClick(v: View?) {
        when (v?.id) {
            R.id.find_id_step1_send_number_btn -> {
                var sendbtn = findViewById<TextView>(R.id.find_id_step1_send_number_btn)
                var resendbtn = findViewById<Button>(R.id.find_id_step1_resend_btn)
                var okaybtn = findViewById<Button>(R.id.find_id_step1_okay_btn)
                var ctEt = findViewById<EditText>(R.id.find_id_step1_certification_number_et)
                var timertv = findViewById<TextView>(R.id.find_id_step1_timer)
                var remessagetv = findViewById<TextView>(R.id.find_id_step1_resend_message_tv)


                if (check) {
                    sendbtn.visibility = View.VISIBLE
                    check = false
                } else {
                    sendbtn.visibility = View.GONE
                    resendbtn.visibility = View.VISIBLE
                    check = true

                    okaybtn.visibility = View.VISIBLE
                    ctEt.visibility = View.VISIBLE
                    timertv.visibility = View.VISIBLE
                    remessagetv.visibility = View.VISIBLE

                    starttimer()


                }
            }
        }
    }

        fun onClick2(v2: View?) {
            when (v2?.id) {
                R.id.find_id_step1_okay_btn -> {
                    var okaybtn = findViewById<Button>(R.id.find_id_step1_okay_btn)
                    var correctbtn = findViewById<Button>(R.id.find_id_step1_correct_btn)

                    if (check2) {
                        okaybtn.visibility = View.GONE
                        correctbtn.visibility = View.VISIBLE
                        check2 = false
                    } else {
                        okaybtn.visibility = View.VISIBLE
                        correctbtn.visibility = View.GONE
                        check2 = true



           }
                }
            }
        }


    }


