package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentFindIdStep1Binding
import java.util.*
import kotlin.concurrent.timer

class FindIdStep1Fragment : Fragment() {
    var check = true
    var mCountDown = 2
    var sCountDown = 59
    var timerTask: Timer? = null

    lateinit var binding: FragmentFindIdStep1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindIdStep1Binding.inflate(inflater, container, false)

        binding.findIdStep1FindBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.find_id_fl, FindIdStep2Fragment())
                addToBackStack(null)
                commit()
            }
        }
        binding.findIdStep1SendNumberBtn.setOnClickListener {
            if (check) {
                binding.findIdStep1SendNumberBtn.visibility = View.VISIBLE
                check = false
            } else {
                binding.findIdStep1SendNumberBtn.visibility = View.GONE
                binding.findIdStep1ResendBtn.visibility = View.VISIBLE
                check = true

                binding.findIdStep1OkayBtn.visibility = View.VISIBLE
                binding.findIdStep1CertificationNumberEt.visibility = View.VISIBLE
                binding.findIdStep1Timer.visibility = View.VISIBLE
                binding.findIdStep1ResendMessageTv.visibility = View.VISIBLE
                startTimer()
            }
        }

        binding.findIdStep1OkayBtn.setOnClickListener {
            if (check) {
                binding.findIdStep1OkayBtn.visibility = View.GONE
                binding.findIdStep1CorrectBtn.visibility = View.VISIBLE
                check = false
            } else {
                binding.findIdStep1OkayBtn.visibility = View.VISIBLE
                binding.findIdStep1CorrectBtn.visibility = View.GONE
                check = true
            }
        }
        return binding.root
    }

    fun startTimer() {
        timerTask = timer(period = 1000) {
            val sec = "%02d".format(sCountDown)
            if (mCountDown == 0 && sCountDown == 0) {
                timerTask?.cancel()
            }
            if (sCountDown == 0) {
                sCountDown = 60
                if (mCountDown >= 1) {
                    mCountDown--
                }
            }
            sCountDown--
            requireActivity().runOnUiThread {
                binding.findIdStep1Timer.text = "남은 시간 ${mCountDown} : ${sec}"
            }
        }
    }

//    private fun findId(){
//
////        val phonenumber = findViewById<EditText>(R.id.find_id_step1_phone_number_et)
//        val phoneNumber : String = binding.findIdStep1PhoneNumberEt.text.toString()
//
//        val authService = AuthService()
//        authService.setFindIdView(this)
//        authService.findId(User(phoneNumber))
//    }
//    override fun onFindIdSuccess(code:Int, result: FindIdResult) {
//        when(code){
//            1000 -> {
//                startActivity(Intent(this, MainActivity::class.java))
//            }
//        }
//    }
//    override fun onFindIdFailure() {
//        Log.d("onFindIdFailure", "!!!!!")
//    }


}