package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginView{
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginForgetMessageTv.setOnClickListener {
            startActivity(Intent(this, FindActivity::class.java))
        }

        binding.loginSingUpBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginSingInBtn.setOnClickListener {
            login()
        }


        binding.loginTestBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


    }

    private fun login(){
        if (binding.loginIdEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            Log.d("TEST","Please input email")
            return
        }
        if(binding.loginPwEt.text.toString().isEmpty()){
            Toast.makeText(this, "비빌번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            Log.d("TEST","Please input password")
            return
        }

        val email : String = binding.loginIdEt.text.toString()
        val pw : String = binding.loginPwEt.text.toString()

        val authService = AuthService()
        authService.setLoginView(this)
        authService.login(User(email, pw))
    }

    private fun saveJwt(jwt:String){
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt", jwt)
        editor.apply()
    }

    override fun onLoginSuccess(code:Int, jwt:String) {
        when(code){
            1000 -> {
                saveJwt(jwt)
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    override fun onLoginFailure() {

    }

}