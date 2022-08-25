package com.example.nanuer


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentFindIdStep1Binding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.timer

class FindIdStep1Fragment : Fragment() ,FindIdView{
    var timerTask: Timer? = null
    var check = true
    var certificationCode = ""

    lateinit var binding: FragmentFindIdStep1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindIdStep1Binding.inflate(inflater, container, false)



        binding.findIdStep1SendNumberBtn.setOnClickListener {
            handleSendBtn()
        }

        binding.findIdStep1ResendBtn.setOnClickListener {
            // ~재전송 처리~
            init()
            getCode(binding.findIdStep1PhoneNumberEt.text.toString())

            // visibility 처리
            binding.findIdStep1CorrectBtn.visibility = View.GONE
            binding.findIdStep1NotCorrectBtn.visibility = View.GONE
            binding.findIdStep1OkayBtn.visibility = View.VISIBLE

            // timer reset
        }

        binding.findIdStep1OkayBtn.setOnClickListener {
            binding.findIdStep1OkayBtn.visibility = View.GONE
            //인증 성공
            if (certificationCode==binding.findIdStep1CertificationCodeEt.text.toString()) {
                binding.findIdStep1CorrectBtn.visibility = View.VISIBLE
                binding.findIdStep1NotCorrectBtn.visibility = View.GONE
            } else {// 인증 실패
                binding.findIdStep1CorrectBtn.visibility = View.GONE
                binding.findIdStep1NotCorrectBtn.visibility = View.VISIBLE
            }
        }

        binding.findIdStep1FindCorrectBtn.setOnClickListener {
            findId()
            handleCode()
        }
        return binding.root
    }

    private fun handleSendBtn(){
        if(binding.findIdStep1PhoneNumberEt.text.toString()==""){
            Toast.makeText(requireContext(),"휴대폰 번호를 입력해주세요.",Toast.LENGTH_SHORT).show()
            return
        }
        binding.findIdStep1SendNumberBtn.visibility = View.GONE
        binding.findIdStep1ResendBtn.visibility = View.VISIBLE
        binding.findIdStep1CertificationCodeRl.visibility = View.VISIBLE
        binding.findIdStep1ResendMessageTv.visibility = View.VISIBLE
        binding.findIdStep1Timer.visibility = View.VISIBLE
        startTimer(2, 59)
        getCode(binding.findIdStep1PhoneNumberEt.text.toString())
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

    override fun onStart() {
        super.onStart()
        Log.d("size","findId" )
        //findId()
    }

    private fun findId(){

        val phoneNumber : String = binding.findIdStep1PhoneNumberEt.text.toString()
        Log.d("GGGG","GGGGG")
        val authService = AuthService()
        authService.setFindIdView(this)
        authService.findId(phoneNumber)
    }
    override fun onFindIdSuccess(result: String) {
        moveToStep2(result)
    }

    override fun onFindIdFailure(code: Int, msg: String) {
        Log.d("onFindIdFailure", "!!!!!")

    }

    private fun moveToStep2(findIdResult: String) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.find_id_fl, FindIdStep2Fragment().apply{
                arguments = Bundle().apply {
                    val gson = Gson()
                    val findIdJson = gson.toJson(findIdResult)
                    putString("Email", findIdJson)

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
        if(binding.findIdStep1CertificationCodeEt.text.isEmpty()){
            Toast.makeText(activity, "인증번호를 입력하지 않으셨습니다", Toast.LENGTH_SHORT).show()
            Log.d("CERTIFICATIONCODE","NOT INPUT")
            return
        }
        binding.findIdStep1OkayBtn.visibility = View.GONE
        Log.d("CODE",certificationCode)
        if(certificationCode==binding.findIdStep1CertificationCodeEt.text.toString()){
            binding.findIdStep1CorrectBtn.visibility = View.VISIBLE
            binding.findIdStep1NotCorrectBtn.visibility = View.GONE
        }else{
            binding.findIdStep1CorrectBtn.visibility = View.GONE
            binding.findIdStep1NotCorrectBtn.visibility = View.VISIBLE
        }
    }


    }











