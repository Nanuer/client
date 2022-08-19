package com.example.nanuer

import com.google.gson.annotations.SerializedName

data class GetRoomAndUserIdResponse(
    @SerializedName(value="isSuccess")val isSuccess:Boolean,
    @SerializedName(value="code")val code:Int,
    @SerializedName(value="message")val message:String,
    @SerializedName(value="result")val result:GetRoomAndUserIdResult?
)

data class GetRoomAndUserIdResult(
    @SerializedName(value="userId")val userId:Int,
    @SerializedName(value="roomNumber")val roomNumber:Int,
)
