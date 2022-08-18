package com.example.nanuer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatRetrofitInterface {
    @GET("/chat/getInfo")
    fun getRoomIdAndUserId(@Query("post_id")post_id:Int): Call<GetRoomAndUserIdResponse>
}