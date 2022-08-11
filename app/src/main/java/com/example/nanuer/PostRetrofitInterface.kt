package com.example.nanuer

import retrofit2.Call
import retrofit2.http.*

interface PostRetrofitInterface {
    @GET("/post/all")
    fun getPosts(): Call<PostResponse>

    @POST("/post")
    fun makePost(@Body post: Post): Call<NormalResponse>

    @DELETE("/post/{post_id}")
    fun deletePost(@Path("post_id") post_id:Int): Call<NormalResponse>
}