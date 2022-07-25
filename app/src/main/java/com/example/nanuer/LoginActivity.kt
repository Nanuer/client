package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(){
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

    }

}