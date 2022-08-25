package com.example.nanuer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.timer

class SignUpActivity : AppCompatActivity(), SignUpView {
    lateinit var binding: ActivitySignupBinding
    var timerTask: Timer? = null
    var certificationCode = ""
    var univs = arrayOf("대학 선택", "숭실대", "인하대", "한양대에리카")

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
            handleSendBtn()
        }

        binding.signupResendBtn.setOnClickListener {
            handleResendBtn()
        }

        binding.signupOkayBtn.setOnClickListener {
            handleCode()
        }

        handleTerms()

        handleSpinner()
    }

    private fun handleSendBtn(){
        if(binding.signupPhoneNumberEt.text.toString()==""){
            Toast.makeText(this,"휴대폰 번호를 입력해주세요.",Toast.LENGTH_SHORT).show()
            return
        }
        binding.signupSendNumberBtn.visibility = View.GONE
        binding.signupResendBtn.visibility = View.VISIBLE
        binding.signupCertificationCodeRl.visibility = View.VISIBLE
        binding.signupResendMessageTv.visibility = View.VISIBLE
        binding.signupTimer.visibility = View.VISIBLE
        startTimer(2, 59)
        getCode(binding.signupPhoneNumberEt.text.toString())
    }

    private fun handleResendBtn(){
        // ~재전송 처리~
        getCode(binding.signupPhoneNumberEt.text.toString())

        // visibility 처리
        binding.signupCorrectBtn.visibility = View.GONE
        binding.signupNotCorrectBtn.visibility = View.GONE
        binding.signupOkayBtn.visibility = View.VISIBLE

        // timer reset
        timerTask?.cancel()
        startTimer(2, 59)
    }

    private fun handleTerms(){
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

    private fun handleSpinner(){
        val spinner = binding.signupUnivSpinner
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, univs
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                Log.d("TEST", univs[position])
//                Toast.makeText(applicationContext, univs.get(position), Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
//                Toast.makeText(applicationContext, "선택되지 않음", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getUser(): User{
        val email: String = binding.signupEmailEt.text.toString()
        val pw: String = binding.signupPwEt.text.toString()
        val nickname : String = binding.signupNicknameEt.text.toString()
        val phoneNumber: String = binding.signupPhoneNumberEt.text.toString()
        val birth: String = binding.signupBirthEt.text.toString()
        val name : String = binding.signupNameEt.text.toString()
        val university : String = binding.signupUnivSpinner.selectedItem.toString()
        return User(email,pw,name,nickname,phoneNumber,birth,university=university)
    }

    private fun signUp(){
        if(binding.signupNameEt.text.isEmpty() || binding.signupNicknameEt.text.isEmpty() || binding.signupEmailEt.text.isEmpty() || binding.signupPwEt.text.isEmpty()|| binding.signupBirthEt.text.isEmpty()){
            Toast.makeText(this,"이메일, 비밀번호, 이름, 닉네임 또는 생년월일을 입력하지 않았습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.signupPwEt.text.toString() != binding.signupPwCheckEt.text.toString()){
            Toast.makeText(this,"비빌번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.signupUnivSpinner.selectedItemPosition==0){
            Toast.makeText(this,"대학교를 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.signupCorrectBtn.visibility != View.VISIBLE){
            Toast.makeText(this,"휴대폰 번호로 인증해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if(!(binding.signupTerm1Cb.isChecked&&binding.signupTerm2Cb.isChecked)){
            Toast.makeText(this,"필수 약관들을 모두 동의하지 않으셨습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val authService = AuthService()
        authService.setSignUpView(this)
        authService.signUp(getUser())
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
        if(binding.signupCertificationCodeEt.text.isEmpty()){
            Toast.makeText(this,"인증번호를 입력하지 않으셨습니다", Toast.LENGTH_SHORT).show()
            Log.d("CERTIFICATIONCODE","NOT INPUT")
            return
        }
        binding.signupOkayBtn.visibility = View.GONE
        Log.d("CODE",certificationCode)
        if(certificationCode==binding.signupCertificationCodeEt.text.toString()){
            binding.signupCorrectBtn.visibility = View.VISIBLE
            binding.signupNotCorrectBtn.visibility = View.GONE
        }else{
            binding.signupCorrectBtn.visibility = View.GONE
            binding.signupNotCorrectBtn.visibility = View.VISIBLE
        }
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