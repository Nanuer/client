package com.example.nanuer

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageService {
    private lateinit var getMyPostsView: GetMyPostsView
    private lateinit var getHeartPostsView: GetHeartPostsView

    fun setGetMyPostsView(getMyPostsView: GetMyPostsView){
        this.getMyPostsView = getMyPostsView
    }
    fun setGetHeartPostsView(getHeartPostsView: GetHeartPostsView) {
        this.getHeartPostsView = getHeartPostsView
    }

    fun getMyPosts(jwt : String){
        val mypageService = getRetrofit().create(MypageRetrofitInterface::class.java)
        mypageService.getMyPosts(jwt).enqueue(object: Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.d("GETMYPOSTS/SUCCESS", response.toString())
                val resp: PostResponse = response.body()!!
                Log.d("GETMYPOSTS/SUCCESS", resp.toString())
                when(val code = resp.code){
                    1000-> getMyPostsView.onGetMyPostsSuccess(resp.result)
                    else-> getMyPostsView.onGetMyPostsFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.d("GETMYPOSTS/FAILURE", t.message.toString())
            }

        })
    }

    fun getHeartPosts(jwt : String){
        val mypageService = getRetrofit().create(MypageRetrofitInterface::class.java)
        mypageService.getHeartPosts(jwt).enqueue(object: Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.d("GETHEARTPOSTS/SUCCESS", response.toString())
                val resp: PostResponse = response.body()!!
                Log.d("GETHEARTPOSTS/SUCCESS", resp.toString())
                when(val code = resp.code){
                    1000-> getHeartPostsView.onGetHeartPostsSuccess(resp.result)
                    else-> getHeartPostsView.onGetHeartPostsFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.d("GETHEARTPOSTS/FAILURE", t.message.toString())
            }
        })
    }

}