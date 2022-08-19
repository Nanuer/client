package com.example.nanuer

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatService {
    private lateinit var getRoomAndUserIdView : GetRoomAndUserIdView

    fun setGetRoomIdAndUserIdView(getRoomAndUserIdView: GetRoomAndUserIdView){
        this.getRoomAndUserIdView = getRoomAndUserIdView
    }

    fun getRoomIdAndUserId(jwt:String, postId:Int){
        val ChatService = getRetrofit().create(ChatRetrofitInterface::class.java)
        ChatService.getRoomIdAndUserId(jwt, postId).enqueue(object: Callback<GetRoomAndUserIdResponse> {
            override fun onResponse(call: Call<GetRoomAndUserIdResponse>, response: Response<GetRoomAndUserIdResponse>) {
                Log.d("RoomAndUserId/SUCCESS", response.toString())
                val resp: GetRoomAndUserIdResponse = response.body()!!
                Log.d("RoomAndUserId/SUCCESS", resp.toString())
                when(val code = resp.code){
                    1000-> {
                        getRoomAndUserIdView.onGetRoomAndUserIdSuccess(resp.result!!)
                    }
                    else-> getRoomAndUserIdView.onGetRoomAndUserIdFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<GetRoomAndUserIdResponse>, t: Throwable) {
                Log.d("RoomAndUserId/FAILURE", t.message.toString())
            }

        })
    }
}