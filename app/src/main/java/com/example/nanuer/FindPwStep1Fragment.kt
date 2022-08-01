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
    var mCountDown = 2
    var sCountDown = 59
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
            if (check) {
                binding.findPwStep1SendNumberBtn.visibility = View.VISIBLE
                check = false
            } else {
                binding.findPwStep1SendNumberBtn.visibility = View.GONE
                binding.findPwStep1ResendBtn.visibility = View.VISIBLE
                check = true

                binding.findPwStep1OkayBtn.visibility = View.VISIBLE
                binding.findPwStep1CertificationNumberEt.visibility = View.VISIBLE
                binding.findPwStep1Timer.visibility = View.VISIBLE
                binding.findPwStep1ResendMessageTv.visibility = View.VISIBLE
                startTimer()
            }
        }

        binding.findPwStep1OkayBtn.setOnClickListener {
            if (check) {
                binding.findPwStep1OkayBtn.visibility = View.GONE
                binding.findPwStep1CorrectBtn.visibility = View.VISIBLE
                check = false
            } else {
                binding.findPwStep1OkayBtn.visibility = View.VISIBLE
                binding.findPwStep1CorrectBtn.visibility = View.GONE
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
                binding.findPwStep1Timer.text = "남은 시간 ${mCountDown} : ${sec}"
            }
        }
    }
}