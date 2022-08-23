package com.example.nanuer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivityBossBinding

class BossActivity :AppCompatActivity(){
    lateinit var binding: ActivityBossBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBossBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bossOkBtn.setOnClickListener {
            finish()
        }
    }
}