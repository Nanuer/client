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


    var id_num_btn: Button? = null
    var id_okay_btn: Button? = null

    var pw_num_btn: Button? = null
    var pw_okay_btn: Button? = null

    var check = true
    var mCountDown = 2
    var sCountDown = 59
    var timerTask: Timer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id_clickbtn()
        id_clickbtn2()

        pw_clickbtn()
        pw_clickbtn2()

        binding.findDeleteLogoIv.setOnClickListener {
            finish()
        }

        val findAdapter = FindVPAdapter(this)

        binding.findVp.adapter = findAdapter
        binding.findVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.findTb, binding.findVp) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()

        id_num_btn = findViewById<Button>(R.id.find_id_step1_send_number_btn)
        id_okay_btn = findViewById<Button>(R.id.find_id_step1_okay_btn)

        pw_num_btn = findViewById<Button>(R.id.find_id_step1_send_number_btn)
        pw_okay_btn = findViewById<Button>(R.id.find_id_step1_okay_btn)


    }

    fun id_starttimer() {

        val timecheck = findViewById<TextView>(R.id.find_id_step1_timer)

        timerTask = timer(period = 1000) {


            val sec = "%02d".format(sCountDown)

            if(mCountDown==0&&sCountDown ==0){
                timerTask?.cancel()
            }

            if(sCountDown == 0){
                sCountDown = 60

                if(mCountDown>=1){
                    mCountDown--
                }
            }
            sCountDown--
            runOnUiThread {
                timecheck?.text = "남은 시간 ${mCountDown} : ${sec}"

            }
        }
    }

    fun pw_starttimer() {

        val timecheck = findViewById<TextView>(R.id.find_pw_step1_timer)

        timerTask = timer(period = 1000) {


            val sec = "%02d".format(sCountDown)

            if(mCountDown==0&&sCountDown ==0){
                timerTask?.cancel()
            }

            if(sCountDown == 0){
                sCountDown = 60

                if(mCountDown>=1){
                    mCountDown--
                }
            }
            sCountDown--
            runOnUiThread {
                timecheck?.text = "남은 시간 ${mCountDown} : ${sec}"

            }
        }
    }



    fun id_clickbtn() {
        id_num_btn?.setOnClickListener { id_onClick(id_num_btn) }
    }

    fun id_clickbtn2() {
        id_okay_btn?.setOnClickListener { id_onClick2(id_okay_btn) }
    }

    fun pw_clickbtn() {
        pw_num_btn?.setOnClickListener { pw_onClick(pw_num_btn) }
    }

    fun pw_clickbtn2() {
        pw_okay_btn?.setOnClickListener { pw_onClick2(pw_okay_btn) }
    }


    fun id_onClick(v: View?) {
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

                    id_starttimer()


                }
            }
        }
    }

        fun id_onClick2(v: View?) {
            when (v?.id) {
                R.id.find_id_step1_okay_btn -> {
                    var okaybtn = findViewById<Button>(R.id.find_id_step1_okay_btn)
                    var correctbtn = findViewById<Button>(R.id.find_id_step1_correct_btn)

                    if (check) {
                        okaybtn.visibility = View.GONE
                        correctbtn.visibility = View.VISIBLE
                        check = false
                    } else {
                        okaybtn.visibility = View.VISIBLE
                        correctbtn.visibility = View.GONE
                        check = true


           }
                }
            }
        }

    fun pw_onClick(v: View?) {
        when (v?.id) {
            R.id.find_pw_step1_send_number_btn -> {
                var sendbtn = findViewById<TextView>(R.id.find_pw_step1_send_number_btn)
                var resendbtn = findViewById<Button>(R.id.find_pw_step1_resend_btn)
                var okaybtn = findViewById<Button>(R.id.find_pw_step1_okay_btn)
                var ctEt = findViewById<EditText>(R.id.find_pw_step1_certification_number_et)
                var timertv = findViewById<TextView>(R.id.find_pw_step1_timer)
                var remessagetv = findViewById<TextView>(R.id.find_pw_step1_resend_message_tv)


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

                    pw_starttimer()


                }
            }
        }
    }

    fun pw_onClick2(v: View?) {
        when (v?.id) {
            R.id.find_pw_step1_okay_btn -> {
                var okaybtn = findViewById<Button>(R.id.find_pw_step1_okay_btn)
                var correctbtn = findViewById<Button>(R.id.find_pw_step1_correct_btn)

                if (check) {
                    okaybtn.visibility = View.GONE
                    correctbtn.visibility = View.VISIBLE
                    check = false
                } else {
                    okaybtn.visibility = View.VISIBLE
                    correctbtn.visibility = View.GONE
                    check = true


                }
            }
        }
    }


    }


