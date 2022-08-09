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
    fun findId(@Query("phone") phone:String): Call<FindIdResponse>
//<<<<<<< HEAD
    //fun getEmail(@Query("email")email:String): Call<GetEmailResponse>


//=======
//>>>>>>> fdeb5ef8847474e57abd33c9e0ee49ed75810bdc
}