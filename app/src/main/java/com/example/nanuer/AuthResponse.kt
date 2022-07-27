package com.example.nanuer

import com.google.gson.annotations.SerializedName;

data class AuthResponse(
    @SerializedName(value="isSuccess")val isSuccess:Boolean,
    @SerializedName(value="code")val code:Int,
    @SerializedName(value="message")val message:String,
    @SerializedName(value="result")val result:Result?
    )

data class Result(
    @SerializedName(value="created_date")val created_date:String,
    @SerializedName(value="modified_date")val modified_date:String,
    @SerializedName(value="userId")val userId:Int,
    @SerializedName(value="email")val email:String,
    @SerializedName(value="name")val name:String,
    @SerializedName(value="password")val password:String,
    @SerializedName(value="nickName")val nickName:String,
    @SerializedName(value="phone")val phone:String,
    @SerializedName(value="birth")val birth:String,
    @SerializedName(value="profileImg")val profileImg:String,
    @SerializedName(value="university")val university:String,
    @SerializedName(value="userStatus")val userStatus:String,
    @SerializedName(value="userScore")val userScore:String,
    @SerializedName(value="myPageEntity")val myPageEntity:String,
    @SerializedName(value="postEntities")val postEntities:String,
    @SerializedName(value="role")val role:String,
    @SerializedName(value="createdDate")val createdDate:String,
    @SerializedName(value="modifiedDate")val modifiedDate:String,
)