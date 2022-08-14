package com.example.nanuer

import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {
    @POST("/user/join")
    fun signUp(@Body user:User): Call<AuthResponse>

    @POST("/user/login")
    fun login(@Body user:User): Call<LoginResponse>

    @GET("/message/send")
    fun getCode(@Query("phone") phone:String) : Call<GetCodeResponse>

    @GET("/user/getEmail")

    fun findId(@Query("phone") phone:String): Call<FindIdResponse>

    @GET("/user/userInfo")
    fun getUserId(@Header("X-AUTH_TOKEN")token : String): Call<NormalResponse>

    @PATCH("user/updatePw")
    fun upDatePw(@Field("phone") phone:String,
                 @Field("password") password:String): Call<UpdatePwResponse>
}