package com.example.nanuer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MypageRetrofitInterface {
    @GET("/mypage/my-posts")
    fun getMyPosts(@Header("X-AUTH-TOKEN")token : String) : Call<PostResponse>

    @GET("/mypage/heart-posts")
    fun getHeartPosts(@Header("X-AUTH-TOKEN")token : String): Call<PostResponse>
}