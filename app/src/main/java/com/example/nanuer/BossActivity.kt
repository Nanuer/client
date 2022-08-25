package com.example.nanuer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivityBossBinding

class BossActivity :AppCompatActivity(),UpdateProgressView{
    lateinit var binding: ActivityBossBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBossBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bossOkBtn.setOnClickListener {
            updateProgress()
        }
    }

    private fun updateProgress(){
        val postId = intent.getIntExtra("postId",0)
        val jwt = getJwt()
        val postService = PostService()
        postService.setUpdateProgressView(this)
        postService.updateProgress(jwt!!, postId)
    }

    private fun getJwt():String?{
        val spf = this.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("jwt","0")
    }

    override fun onUpdateProgressSuccess() {
        finish()
    }

    override fun onUpdateProgressFailure(code: Int, msg: String) {

    }
}