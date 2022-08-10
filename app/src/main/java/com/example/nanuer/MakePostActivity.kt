package com.example.nanuer


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivityMakePostBinding

class MakePostActivity : AppCompatActivity(), MakePostView {
    lateinit var binding: ActivityMakePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.makePostBackIv.setOnClickListener {
            finish()
        }

        binding.makePostFooterOkTv.setOnClickListener {
            makePost()
        }
    }

    private fun makePost(){
        val title : String = binding.makePostTitleEt.text.toString()
        val content : String = binding.makePostContentEt.text.toString()
        val delivery_cost : String = binding.makePostDeliveryCostEt.text.toString()

        val postService=PostService()
        postService.setPostView(this)
        postService.makePost(Post(title=title,content=content,delivery_cost=delivery_cost))
    }

    override fun onMakePostSuccess() {
        finish()
    }

    override fun onMakePostFailure(code:Int, msg:String) {
        when(code){
            2018,2019 -> Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
        }
    }
}