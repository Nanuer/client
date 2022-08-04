package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivityLoginBinding
import com.example.nanuer.databinding.ActivityMakePostBinding

class MakePostActivity : AppCompatActivity() {
    lateinit var binding: ActivityMakePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.makePostBackIv.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}