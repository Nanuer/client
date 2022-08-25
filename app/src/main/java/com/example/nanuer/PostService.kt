package com.example.nanuer

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostService {
    private lateinit var makePostView: MakePostView
    private lateinit var getPostsView: GetPostsView
    private lateinit var deletePostView: DeletePostView
    private lateinit var updateProgressView: UpdateProgressView
    private lateinit var getProgressView: GetProgressView

    fun setPostView(makePostView: MakePostView){
        this.makePostView = makePostView
    }
    fun setGetPostsView(getPostsView: GetPostsView){
        this.getPostsView = getPostsView
    }
    fun setDeletePostView(deletePostView: DeletePostView){
        this.deletePostView = deletePostView
    }
    fun setUpdateProgressView(updateProgressView: UpdateProgressView){
        this.updateProgressView = updateProgressView
    }
    fun setGetProgressView(getProgressView: GetProgressView){
        this.getProgressView = getProgressView
    }

    fun makePost(jwt:String, post:Post){
        val postService = getRetrofit().create(PostRetrofitInterface::class.java)
        postService.makePost(jwt, post).enqueue(object: Callback<NormalResponse> {
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

    fun getPosts(jwt:String){
        val postService = getRetrofit().create(PostRetrofitInterface::class.java)
        postService.getPosts(jwt).enqueue(object: Callback<PostResponse> {
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
                Log.d("GETPOSTS/FAILURE", t.message.toString())
            }
        })
    }

    fun getPostsByUnivAndQuery(jwt:String, query: String?){
        val postService = getRetrofit().create(PostRetrofitInterface::class.java)
        postService.getPostsByUnivAndQuery(jwt, query).enqueue(object: Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.d("GETPOSTSBYUNIV/SUCCESS", response.toString())
                val resp: PostResponse = response.body()!!
                Log.d("GETPOSTSBYUNIV/SUCCESS", resp.toString())
                when(val code = resp.code){
                    1000-> getPostsView.onGetPostsSuccess(resp.result)
                    else-> getPostsView.onGetPostsFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.d("GETPOSTSBYUNIV/FAILURE", t.message.toString())
            }
        })
    }

    fun deletePost(jwt:String, post_id:Int){
        val postService = getRetrofit().create(PostRetrofitInterface::class.java)
        postService.deletePost(jwt, post_id).enqueue(object: Callback<NormalResponse> {
            override fun onResponse(call: Call<NormalResponse>, response: Response<NormalResponse>) {
                Log.d("DELETEPOST/SUCCESS", response.toString())
                val resp: NormalResponse = response.body()!!
                Log.d("DELETEPOST/SUCCESS", resp.toString())
                when(val code = resp.code){
                    1000-> deletePostView.onDeletePostSuccess()
                    else-> deletePostView.onDeletePostFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<NormalResponse>, t: Throwable) {
                Log.d("DELETEPOST/FAILURE", t.message.toString())
            }
        })
    }

    fun updateProgress(jwt:String, post_id:Int){
        val postService = getRetrofit().create(PostRetrofitInterface::class.java)
        postService.updateProgress(jwt, post_id).enqueue(object: Callback<NormalResponse> {
            override fun onResponse(call: Call<NormalResponse>, response: Response<NormalResponse>) {
                Log.d("UPDATEPROGRESS/SUCCESS", response.toString())
                val resp: NormalResponse = response.body()!!
                Log.d("UPDATEPROGRESS/SUCCESS", resp.toString())
                when(val code = resp.code){
                    1000-> updateProgressView.onUpdateProgressSuccess()
                    else-> updateProgressView.onUpdateProgressFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<NormalResponse>, t: Throwable) {
                Log.d("UPDATEPROGRESS/FAILURE", t.message.toString())
            }
        })
    }

    fun getProgress(jwt:String, post_id:Int){
        val postService = getRetrofit().create(PostRetrofitInterface::class.java)
        postService.getProgress(jwt, post_id).enqueue(object: Callback<NormalResponse> {
            override fun onResponse(call: Call<NormalResponse>, response: Response<NormalResponse>) {
                Log.d("GETPROGRESS/SUCCESS", response.toString())
                val resp: NormalResponse = response.body()!!
                Log.d("GETPROGRESS/SUCCESS", resp.toString())
                when(val code = resp.code){
                    1000-> getProgressView.onGetProgressSuccess(resp.result)
                    else-> getProgressView.onGetProgressFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<NormalResponse>, t: Throwable) {
                Log.d("GETPROGRESS/FAILURE", t.message.toString())
            }
        })
    }
}