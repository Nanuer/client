package com.example.nanuer

import com.google.gson.annotations.SerializedName;

data class GetEmailResponse (
    @SerializedName(value="isSuccess")val isSuccess:Boolean,
    @SerializedName(value="code")val code:Int,
    @SerializedName(value="message")val message:String,
    @SerializedName(value="result")val result:String
)