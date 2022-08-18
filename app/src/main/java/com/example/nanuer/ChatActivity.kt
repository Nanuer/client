package com.example.nanuer

import android.os.Bundle
import android.os.PersistableBundle
import android.system.Os.socket
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivityChatBinding

class ChatActivity: AppCompatActivity(){
    lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chatPersonalBackIv.setOnClickListener{
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}

