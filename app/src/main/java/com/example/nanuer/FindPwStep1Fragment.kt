package com.example.nanuer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentFindPwStep1Binding
import java.util.*
import kotlin.concurrent.timer

class FindPwStep1Fragment : Fragment() {
    var check = true
    var timerTask: Timer? = null

    lateinit var binding: FragmentFindPwStep1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindPwStep1Binding.inflate(inflater,container,false)

        binding.findPwStep1FindBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.find_pw_fl, FindPwStep2Fragment())
                addToBackStack(null)
                commit()
            }
        }

        binding.findPwStep1SendNumberBtn.setOnClickListener {
            binding.findPwStep1SendNumberBtn.visibility = View.GONE
            binding.findPwStep1ResendBtn.visibility = View.VISIBLE
            binding.findPwStep1CertificationCodeRl.visibility = View.VISIBLE
            binding.findPwStep1ResendMessageTv.visibility = View.VISIBLE
            binding.findPwStep1Timer.visibility = View.VISIBLE
            startTimer(2, 59)
        }

        binding.findPwStep1ResendBtn.setOnClickListener {
            // ~재전송 처리~

            // visibility 처리
            binding.findPwStep1CorrectBtn.visibility = View.GONE
            binding.findPwStep1NotCorrectBtn.visibility = View.GONE
            binding.findPwStep1OkayBtn.visibility = View.VISIBLE

            // timer reset
        }

        binding.findPwStep1OkayBtn.setOnClickListener {
            binding.findPwStep1OkayBtn.visibility = View.GONE
            //인증 성공
            if (check) {
                binding.findPwStep1CorrectBtn.visibility = View.VISIBLE
                binding.findPwStep1NotCorrectBtn.visibility = View.GONE
            } else {// 인증 실패
                binding.findPwStep1CorrectBtn.visibility = View.GONE
                binding.findPwStep1NotCorrectBtn.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        timerTask?.cancel()
    }

    private fun init(){
        binding.findPwStep1IdEt.setText(null)
        binding.findPwStep1PhoneNumberEt.setText(null)
        binding.findPwStep1CertificationCodeEt.setText(null)

        binding.findPwStep1SendNumberBtn.visibility = View.VISIBLE
        binding.findPwStep1ResendBtn.visibility = View.GONE

        binding.findPwStep1CertificationCodeRl.visibility = View.GONE
        binding.findPwStep1OkayBtn.visibility = View.VISIBLE
        binding.findPwStep1CorrectBtn.visibility = View.GONE
        binding.findPwStep1NotCorrectBtn.visibility = View.GONE

        binding.findPwStep1Timer.visibility = View.GONE
        binding.findPwStep1ResendMessageTv.visibility = View.GONE
        timerTask?.cancel()
    }

    fun startTimer(m:Int, s:Int) {
        var mCountDown = m
        var sCountDown = s
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
                binding.findPwStep1Timer.text = "남은 시간 ${mCountDown} : ${sec}"
            }
        }
    }
}