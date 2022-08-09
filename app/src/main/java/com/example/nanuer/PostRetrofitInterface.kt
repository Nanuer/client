package com.example.nanuer

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostRetrofitInterface {
    @GET("/post/all")
    fun getPosts(): Call<PostResponse>

    @POST("/post")
    fun makePost(@Body post: Post): Call<NormalResponse>
}