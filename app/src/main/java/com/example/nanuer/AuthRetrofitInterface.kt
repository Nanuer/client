package com.example.nanuer

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthRetrofitInterface {
    @POST("/user/join")
    fun signUp(@Body user:User): Call<AuthResponse>

    @POST("/user/login")
    fun login(@Body user:User): Call<LoginResponse>

    @GET("/message/send")
    fun getCode(@Query("phone") phone:String) : Call<GetCodeResponse>

    @GET("/user/getEmail")
    fun findId(@Body user: User): Call<FindIdResponse>
    fun getEmail(@Query("email")email:String): Call<GetEmailResponse>

//    @POST("/post")
//    fun write(@Body makePost: MakePost): Call<>
}