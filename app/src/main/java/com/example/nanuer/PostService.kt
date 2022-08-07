package com.example.nanuer

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostService {
    private lateinit var postView: PostView

    fun setPostView(postView: PostView){
        this.postView = postView
    }

    fun makePost(post:Post){
        val postService = getRetrofit().create(PostRetrofitInterface::class.java)
        postService.makePost(post).enqueue(object: Callback<NormalResponse> {
            override fun onResponse(call: Call<NormalResponse>, response: Response<NormalResponse>) {
                Log.d("MAKEPOST/SUCCESS", response.toString())
                val resp: NormalResponse = response.body()!!
                Log.d("MAKEPOST/SUCCESS", resp.toString())
                when(val code = resp.code){
                    1000-> postView.onPostSuccess()
                    else-> postView.onPostFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<NormalResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }
        })
    }
}