package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity(), SignUpView {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.singupBackIv.setOnClickListener{
            finish()
        }

        binding.signupBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.signupBtn.setOnClickListener {
            signUp()
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

    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure() {
        Log.d("onSignUpFailure", "!!!!!")
    }
}