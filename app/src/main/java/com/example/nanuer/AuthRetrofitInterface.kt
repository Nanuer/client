package com.example.nanuer

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/user/join")
    fun signUp(@Body user:User): Call<AuthResponse>

    @POST("/user/login")
    fun login(@Body user:User): Call<LoginResponse>

    @GET("/user/getEmail")
    fun findId(@Body user: User): Call<FindIdResponse>

//    @POST("/post")
//    fun write(@Body makePost: MakePost): Call<>
}