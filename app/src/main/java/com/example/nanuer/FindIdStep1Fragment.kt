package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentFindIdStep1Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.timer

class FindIdStep1Fragment : Fragment() ,FindIdView{
    var timerTask: Timer? = null
    var check = true

    lateinit var binding: FragmentFindIdStep1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindIdStep1Binding.inflate(inflater, container, false)

//        binding.findIdStep1FindBtn.setOnClickListener {
//            parentFragmentManager.beginTransaction().apply {
//                replace(R.id.find_id_fl, FindIdStep2Fragment())
//                addToBackStack(null)
//                commit()
//            }
//        }

        binding.findIdStep1FindBtn.setOnClickListener {
            findId()
        }

        binding.findIdStep1SendNumberBtn.setOnClickListener {
            binding.findIdStep1SendNumberBtn.visibility = View.GONE
            binding.findIdStep1ResendBtn.visibility = View.VISIBLE
            binding.findIdStep1CertificationCodeRl.visibility = View.VISIBLE
            binding.findIdStep1ResendMessageTv.visibility = View.VISIBLE
            binding.findIdStep1Timer.visibility = View.VISIBLE
            startTimer(2, 59)
        }

        binding.findIdStep1ResendBtn.setOnClickListener {
            // ~재전송 처리~

            // visibility 처리
            binding.findIdStep1CorrectBtn.visibility = View.GONE
            binding.findIdStep1NotCorrectBtn.visibility = View.GONE
            binding.findIdStep1OkayBtn.visibility = View.VISIBLE

            // timer reset
        }

        binding.findIdStep1OkayBtn.setOnClickListener {
            binding.findIdStep1OkayBtn.visibility = View.GONE
            //인증 성공
            if (check) {
                binding.findIdStep1CorrectBtn.visibility = View.VISIBLE
                binding.findIdStep1NotCorrectBtn.visibility = View.GONE
            } else {// 인증 실패
                binding.findIdStep1CorrectBtn.visibility = View.GONE
                binding.findIdStep1NotCorrectBtn.visibility = View.VISIBLE
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
        binding.findIdStep1PhoneNumberEt.setText(null)
        binding.findIdStep1CertificationCodeEt.setText(null)

        binding.findIdStep1SendNumberBtn.visibility = View.VISIBLE
        binding.findIdStep1ResendBtn.visibility = View.GONE

        binding.findIdStep1CertificationCodeRl.visibility = View.GONE
        binding.findIdStep1OkayBtn.visibility = View.VISIBLE
        binding.findIdStep1CorrectBtn.visibility = View.GONE
        binding.findIdStep1NotCorrectBtn.visibility = View.GONE

        binding.findIdStep1Timer.visibility = View.GONE
        binding.findIdStep1ResendMessageTv.visibility = View.GONE
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
                binding.findIdStep1Timer.text = "남은 시간 ${mCountDown} : ${sec}"
            }
        }
    }

    private fun findId(){

        val phoneNumber : String = binding.findIdStep1PhoneNumberEt.text.toString()

        val authService = AuthService()
        authService.setFindIdView(this)
        authService.findId(User(phoneNumber))
    }
    override fun onFindIdSuccess(code:Int, result: String) {

        init()

        binding.findIdStep1FindBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.find_id_fl, FindIdStep2Fragment())
                addToBackStack(null)
                commit()
            }
        }

    }

    override fun onFindIdFailure(code: Int, msg: String) {
        Log.d("onFindIdFailure", "!!!!!")

    }

//    private fun getEmail(email:String){
//        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
//        authService.getEmail(email).enqueue(object: Callback<GetEmailResponse> {
//            override fun onResponse(call: Call<GetEmailResponse>, response: Response<GetEmailResponse>
//            ) {
//                Log.d("CODE/SUCCESS", response.toString())
//                val resp: GetEmailResponse = response.body()!!
//                Log.d("CODE/SUCCESS", resp.toString())
//                when(resp.code){
//                    1000-> userEmail=resp.result
//                    else -> {}
//                }
//            }
//            override fun onFailure(call: Call<GetEmailResponse>, t: Throwable) {
//                Log.d("GET/CODE/FAILURE", t.message.toString())
//            }
//        })
//    }



}