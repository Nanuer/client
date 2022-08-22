package com.example.nanuer

import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {
    @POST("/join")
    fun signUp(@Body user:User): Call<AuthResponse>

    @POST("/login")
    fun login(@Body user:User): Call<LoginResponse>

    @GET("/message/send")
    fun getCode(@Query("phone") phone:String) : Call<GetCodeResponse>

    @GET("/getEmail")
    fun findId(@Query("phone") phone:String): Call<FindIdResponse>

    @GET("/user/userInfo")
    fun getUserId(@Header("X-AUTH-TOKEN")token : String): Call<GetUserIdResponse>

    @GET("/user/info")
    fun getUserInfo(@Header("X-AUTH-TOKEN")token : String): Call<GetUserInfoResponse>

    @PATCH("/updatePw")
    fun upDatePw(@Field("phone") phone:String,
                 @Field("password") password:String): Call<UpdatePwResponse>
}