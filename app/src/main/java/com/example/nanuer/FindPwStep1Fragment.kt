package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentFindPwStep1Binding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.timer

class FindPwStep1Fragment : Fragment(), UpdatePwView {
    var check = true
    var timerTask: Timer? = null
    private lateinit var updatePwView: UpdatePwView
    var certificationCode = ""



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
            getCode(binding.findPwStep1PhoneNumberEt.text.toString())
        }

        binding.findPwStep1ResendBtn.setOnClickListener {
            // ~재전송 처리~
            init()
            getCode(binding.findPwStep1PhoneNumberEt.text.toString())

            // visibility 처리
            binding.findPwStep1CorrectBtn.visibility = View.GONE
            binding.findPwStep1NotCorrectBtn.visibility = View.GONE
            binding.findPwStep1OkayBtn.visibility = View.VISIBLE

            // timer reset
        }

        binding.findPwStep1OkayBtn.setOnClickListener {
            binding.findPwStep1OkayBtn.visibility = View.GONE
            //인증 성공
//            if (check) {
//                binding.findPwStep1CorrectBtn.visibility = View.VISIBLE
//                binding.findPwStep1NotCorrectBtn.visibility = View.GONE
//            } else {// 인증 실패
//                binding.findPwStep1CorrectBtn.visibility = View.GONE
//                binding.findPwStep1NotCorrectBtn.visibility = View.VISIBLE
//            }
            handleCode()
        }

        binding.findPwStep1CorrectBtn.setOnClickListener {
            handleCode()
        }

        binding.findPwStep1FindBtn.setOnClickListener {
            upDatePw()

//            val intent = Intent(activity, LoginActivity::class.java)
//            startActivity(intent)
        }

        return binding.root
    }
    override fun onStart() {
        super.onStart()
        Log.d("size","updatePw" )

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

    private fun upDatePw() {

        val jwt = getJwt()
        val phoneNUmber : String = binding.findPwStep1PhoneNumberEt.text.toString()
        val passWord : String = binding.findIdStep1UpdatePwEt.text.toString()


        val authService = AuthService()
        authService.setUpdatePwview(this)
        authService.upDatePw(jwt!!,User(phoneNUmber))
    }

    private fun getJwt():String?{
        val passWord : String = binding.findIdStep1UpdatePwEt.text.toString()
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("Jwt",passWord)
    }



    override fun onUpdatePwSuccess(result: String) {
        moveToStep2(result)

    }

    override fun onUpdatePwFailure(code: Int, msg: String) {
        Log.d("onUpdatePwFailure", "!!!!!")
    }



    private fun moveToStep2(updatePwResult: String) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.find_pw_fl, FindPwStep2Fragment().apply{
                arguments = Bundle().apply {
                    val gson = Gson()
                    val updatePwJson = gson.toJson(updatePwResult)
                    putString("password", updatePwJson)

                }
            })
            addToBackStack(null)
            commit()
        }
    }

    private fun getCode(phone:String){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.getCode(phone).enqueue(object: Callback<GetCodeResponse> {
            override fun onResponse(call: Call<GetCodeResponse>, response: Response<GetCodeResponse>
            ) {
                Log.d("CODE/SUCCESS", response.toString())
                val resp: GetCodeResponse = response.body()!!
                Log.d("CODE/SUCCESS", resp.toString())
                when(resp.code){
                    1000-> certificationCode=resp.result
                    else -> {}
                }
            }
            override fun onFailure(call: Call<GetCodeResponse>, t: Throwable) {
                Log.d("GET/CODE/FAILURE", t.message.toString())
            }
        })
    }

    private fun handleCode(){
        if(binding.findPwStep1CertificationCodeEt.text.isEmpty()){
            Toast.makeText(activity, "인증번호를 입력하지 않으셨습니다", Toast.LENGTH_SHORT).show()
            Log.d("CERTIFICATIONCODE","NOT INPUT")
            return
        }
        binding.findPwStep1OkayBtn.visibility = View.GONE
        Log.d("CODE",certificationCode)
        if(certificationCode==binding.findPwStep1CertificationCodeEt.text.toString()){
            binding.findPwStep1CorrectBtn.visibility = View.VISIBLE
            binding.findPwStep1NotCorrectBtn.visibility = View.GONE
            binding.findIdStep1UpdatePwEt.visibility = View.VISIBLE
            binding.findIdStep1PwConfirmEt.visibility = View.VISIBLE
            timerTask?.cancel()
            binding.findPwStep1Timer.visibility = View.GONE
            binding.findPwStep1ResendMessageTv.visibility = View.GONE
        }else{
            binding.findPwStep1CorrectBtn.visibility = View.GONE
            binding.findPwStep1NotCorrectBtn.visibility = View.VISIBLE
            binding.findIdStep1UpdatePwEt.visibility = View.GONE
            binding.findIdStep1PwConfirmEt.visibility = View.GONE
        }
    }



}