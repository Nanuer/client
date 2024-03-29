package com.example.nanuer

import retrofit2.Call
import retrofit2.http.*

interface PostRetrofitInterface {
    @GET("/post/all")
    fun getPosts(@Header("X-AUTH-TOKEN")token : String): Call<PostResponse>

    @GET("/post")
    fun getPostsByUnivAndQuery(@Header("X-AUTH-TOKEN")token : String, @Query("query") query: String?): Call<PostResponse>

    @POST("/post")
    fun makePost(@Header("X-AUTH-TOKEN")token : String, @Body post: Post): Call<NormalResponse>

    @PATCH("/post/{post_id}")
    fun deletePost(@Header("X-AUTH-TOKEN")token : String, @Path("post_id") post_id:Int): Call<NormalResponse>

    @PATCH("/post/progress")
    fun updateProgress(@Header("X-AUTH-TOKEN")token : String, @Query("post_id") post_id:Int): Call<NormalResponse>

    @GET("/post/progress")
    fun getProgress(@Header("X-AUTH-TOKEN")token : String, @Query("post_id") post_id: Int): Call<NormalResponse>
}