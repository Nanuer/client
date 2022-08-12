package com.example.nanuer

import retrofit2.Call
import retrofit2.http.*

interface PostRetrofitInterface {
    @GET("/post/all")
    fun getPosts(): Call<PostResponse>

    @GET("/post")
    fun getPostsByUnivAndQuery(@Query("user_id") user_id:Int, @Query("query") query: String?): Call<PostResponse>

    @POST("/post")
    fun makePost(@Body post: Post): Call<NormalResponse>

    @PATCH("/post/{post_id}")
    fun deletePost(@Path("post_id") post_id:Int): Call<NormalResponse>
}