package com.example.nanuer

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PostRetrofitInterface {
    @POST("/post")
    fun makePost(@Body post: Post): Call<NormalResponse>
}