package com.example.nanuer

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostService {
    private lateinit var makePostView: MakePostView
    private lateinit var getPostsView: GetPostsView

    fun setPostView(makePostView: MakePostView){
        this.makePostView = makePostView
    }
    fun setGetPostsView(getPostsView: GetPostsView){
        this.getPostsView = getPostsView
    }

    fun makePost(post:Post){
        val postService = getRetrofit().create(PostRetrofitInterface::class.java)
        postService.makePost(post).enqueue(object: Callback<NormalResponse> {
            override fun onResponse(call: Call<NormalResponse>, response: Response<NormalResponse>) {
                Log.d("MAKEPOST/SUCCESS", response.toString())
                val resp: NormalResponse = response.body()!!
                Log.d("MAKEPOST/SUCCESS", resp.toString())
                when(val code = resp.code){
                    1000-> makePostView.onMakePostSuccess()
                    else-> makePostView.onMakePostFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<NormalResponse>, t: Throwable) {
                Log.d("MAKEPOST/FAILURE", t.message.toString())
            }
        })
    }

    fun getPosts(){
        val postService = getRetrofit().create(PostRetrofitInterface::class.java)
        postService.getPosts().enqueue(object: Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.d("GETPOSTS/SUCCESS", response.toString())
                val resp: PostResponse = response.body()!!
                Log.d("GETPOSTS/SUCCESS", resp.toString())
                when(val code = resp.code){
                    1000-> getPostsView.onGetPostsSuccess(resp.result)
                    else-> getPostsView.onGetPostsFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.d("GETPOST/FAILURE", t.message.toString())
            }
        })
    }
}