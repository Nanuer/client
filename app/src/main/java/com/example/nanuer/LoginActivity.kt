package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginView, JwtIsValidateView{
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Nanuer)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginFindIdPwRl.setOnClickListener {
            init()
            startActivity(Intent(this, FindActivity::class.java))
        }

        binding.loginSignUpRl.setOnClickListener {
            init()
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginSingInBtn.setOnClickListener {
            login()
        }
//        val spf = this.getSharedPreferences("auth", MODE_PRIVATE)
//        val jwt = spf!!.getString("jwt","0")
//        if(jwt!="0"){
//            jwtIsValidate(jwt!!)
//        }

//        binding.loginTestBtn.setOnClickListener {
//            startActivity(Intent(this, MainActivity::class.java))
//        }

    }

    private fun jwtIsValidate(jwt:String){
        val authService = AuthService()
        authService.setJwtIsValidateView(this)
        authService.jwtIsValidate(jwt)
    }

    private fun login(){
        if (binding.loginIdEt.text.toString().isEmpty() || binding.loginPwEt.text.toString().isEmpty()){
            binding.loginWarningTv.visibility = View.VISIBLE
            binding.loginWarningTv.text = "이메일 또는 비밀번호를 입력하지 않았습니다."
            Log.d("TEST","Please input email")
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

    private fun init(){
        binding.loginIdEt.text = null
        binding.loginPwEt.text = null
        binding.loginWarningTv.visibility = View.GONE
    }

    override fun onLoginSuccess(code:Int, jwt:String) {
        when(code){
            1000 -> {
                binding.loginWarningTv.visibility = View.GONE
                saveJwt(jwt)
                init()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onLoginFailure(code:Int, msg:String) {
        when(code){
            3014 -> {
                binding.loginWarningTv.visibility = View.VISIBLE
                binding.loginWarningTv.text = msg
            }
        }
    }

    override fun onJwtIsValidateSuccess(result: Boolean) {
        Log.d("JWTISVALIDATE????",result.toString())
        if(result){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onJwtIsValidateFailure(code: Int, msg: String) {

    }
}