package com.example.nanuer

import com.google.gson.annotations.SerializedName

data class Chat(
    @SerializedName(value="userId")var userId:Int? = null,
    @SerializedName(value="nickName")var nickName:String? = null,
    @SerializedName(value="time")var type:String? = null,
    @SerializedName(value="msg")var msg:String? = null,
    @SerializedName(value="time")var time:String? = null,
    @SerializedName(value="profileImg")var profileImg:String? = null
)
