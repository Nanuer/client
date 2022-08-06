package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivityMakePostBinding
import java.text.SimpleDateFormat

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

    private fun write(){
        val currentTime : Long = System.currentTimeMillis()
        val timeFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")


        val title : String = binding.makePostTitleEt.text.toString()
        val content : String = binding.makePostContentEt.text.toString()
        val time : String = timeFormat.format(currentTime).toString()


    }
}