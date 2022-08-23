package com.example.nanuer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivityAccountBinding

class AccountActivity :AppCompatActivity(){
    lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val account = intent.getStringExtra("account")

        binding.accountAccountTv.text="${account}\n으로 ()를 보내주세요."
    }
}