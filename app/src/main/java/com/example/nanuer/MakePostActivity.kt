package com.example.nanuer


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivityMakePostBinding
import java.text.SimpleDateFormat

class MakePostActivity : AppCompatActivity(), PostView {
    lateinit var binding: ActivityMakePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.makePostBackIv.setOnClickListener {
            finish()
        }

        binding.makePostCreateBtn.setOnClickListener {
            makePost()
        }
    }

    private fun makePost(){
//        val currentTime : Long = System.currentTimeMillis()
//        val timeFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")

        val title : String = binding.makePostTitleEt.text.toString()
        val content : String = binding.makePostContentEt.text.toString()

        val postService=PostService()
        postService.setPostView(this)
        postService.makePost(Post(title,content))
    }

    override fun onPostSuccess() {
        finish()
    }

    override fun onPostFailure(code:Int, msg:String) {
        when(code){
            2018,2019 -> Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
        }
    }
}