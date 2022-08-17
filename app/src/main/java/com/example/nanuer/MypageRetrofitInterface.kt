package com.example.nanuer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MypageRetrofitInterface {
    @GET("/mypage/my-posts")
    fun getMyPosts(@Query("email") email:String) : Call<PostResponse>

    @GET("/mypage/heart-posts")
    fun getHeartPosts(@Query("email") email:String): Call<PostResponse>
}