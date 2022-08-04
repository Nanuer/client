package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivitySignupBinding
import java.util.*
import kotlin.concurrent.timer

class SignUpActivity : AppCompatActivity(), SignUpView {
    lateinit var binding: ActivitySignupBinding
    var timerTask: Timer? = null
    var check = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.singupBackIv.setOnClickListener{
            finish()
        }

        binding.signupBtn.setOnClickListener {
            signUp()
        }

        binding.signupSendNumberBtn.setOnClickListener {
            binding.signupSendNumberBtn.visibility = View.GONE
            binding.signupResendBtn.visibility = View.VISIBLE
            binding.signupCertificationCodeRl.visibility = View.VISIBLE
            binding.signupResendMessageTv.visibility = View.VISIBLE
            binding.signupTimer.visibility = View.VISIBLE
            startTimer(2, 59)
        }

        binding.signupResendBtn.setOnClickListener {
            // ~재전송 처리~

            // visibility 처리
            binding.signupCorrectBtn.visibility = View.GONE
            binding.signupNotCorrectBtn.visibility = View.GONE
            binding.signupOkayBtn.visibility = View.VISIBLE

            // timer reset
        }

        binding.signupOkayBtn.setOnClickListener {
            binding.signupOkayBtn.visibility = View.GONE
            //인증 성공
            if (check) {
                binding.signupCorrectBtn.visibility = View.VISIBLE
                binding.signupNotCorrectBtn.visibility = View.GONE
            } else {// 인증 실패
                binding.signupCorrectBtn.visibility = View.GONE
                binding.signupNotCorrectBtn.visibility = View.VISIBLE
            }
        }

        val listener = CompoundButton.OnCheckedChangeListener {buttonView, isChecked ->
            when(buttonView.id){
                R.id.signup_terms_cb -> {
                    if(isChecked){
                        binding.signupTerm1Cb.isChecked = true
                        binding.signupTerm2Cb.isChecked = true
                        binding.signupTerm3Cb.isChecked = true
                    }else if(binding.signupTerm1Cb.isChecked&&binding.signupTerm2Cb.isChecked&&binding.signupTerm3Cb.isChecked){
                        binding.signupTerm1Cb.isChecked = false
                        binding.signupTerm2Cb.isChecked = false
                        binding.signupTerm3Cb.isChecked = false
                    }
                }
                else -> {
                    binding.signupTermsCb.isChecked = (binding.signupTerm1Cb.isChecked&&binding.signupTerm2Cb.isChecked&&binding.signupTerm3Cb.isChecked)
                }
            }
        }

        binding.signupTermsCb.setOnCheckedChangeListener(listener)
        binding.signupTerm1Cb.setOnCheckedChangeListener(listener)
        binding.signupTerm2Cb.setOnCheckedChangeListener(listener)
        binding.signupTerm3Cb.setOnCheckedChangeListener(listener)
    }

    private fun getUser(): User{
        val email: String = binding.signupEmailEt.text.toString()
        val pw: String = binding.signupPwEt.text.toString()
        val phoneNumber: String = binding.signupPhoneNumberEt.text.toString()

        return User(email,pw,phone=phoneNumber)
    }

    private fun signUp(){
        if(binding.signupPwEt.text.toString() != binding.signupPwCheckEt.text.toString()){
            Toast.makeText(this,"비빌번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            Log.d("TEST","TESTEST")
            return
        }

        if(!(binding.signupTerm1Cb.isChecked&&binding.signupTerm2Cb.isChecked)){
            Toast.makeText(this,"필수 약관들을 모두 동의하지 않으셨습니다.", Toast.LENGTH_SHORT).show()
            Log.d("TEST","NOT AGREE")
            return
        }

        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signUp(getUser())
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
            runOnUiThread {
                binding.signupTimer.text = "남은 시간 ${mCountDown} : ${sec}"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timerTask?.cancel()
    }

    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure() {
        Log.d("onSignUpFailure", "!!!!!")
    }
}