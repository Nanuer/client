package com.example.nanuer

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("isSuccess")val isSuccess:Boolean,
    @SerializedName("code")val code:Int,
    @SerializedName("message")val message:String,
    @SerializedName("result")val result:PostResult
)

data class PostResult(
    @SerializedName("postList") val postList: ArrayList<Post2>
)

data class Post2(
    @SerializedName("created_date")val created_date:String?=null,
    @SerializedName("modified_date")val modified_date:String?=null,
    @SerializedName("postId")val postId:Int?=null,
    @SerializedName("title")val title:String,
    @SerializedName("content")val content:String,
    @SerializedName("view")val view:Int?=null,
    @SerializedName("heartCount")val heartCount:Int?=null,
    @SerializedName("progress")val progress:String?=null,
    @SerializedName("menu")val menu:String,
    @SerializedName("total")val total:Int?=null,
    @SerializedName("location")val location:String?=null,
    @SerializedName("time")val time:String,
    @SerializedName("userEntity")val userEntity:UserEntity?=null,
    @SerializedName("categoryEntity")val categoryEntity:CategoryEntity?=null,
    @SerializedName("createdDate")val createdDate:String?=null,
    @SerializedName("modifiedDate")val modifiedDate:String?=null,
    @SerializedName("cost_info")val cost_info:String?=null,
    @SerializedName("delivery_cost")val delivery_cost:String?=null,
    @SerializedName("post_status")val post_status:Int?=null
)

data class UserEntity(
    @SerializedName("created_date")val created_date:String,
    @SerializedName("modified_date")val modified_date:String,
    @SerializedName("userId")val userId:Int,
    @SerializedName("email")val email:String,
    @SerializedName("name")val name:String,
    @SerializedName("password")val password:String,
    @SerializedName("nickName")val nickName:String,
    @SerializedName("phone")val phone:String,
    @SerializedName("birth")val birth:String,
    @SerializedName("profileImg")val profileImg:String,
    @SerializedName("university")val university:String,
    @SerializedName("userStatus")val userStatus:String,
    @SerializedName("userScore")val userScore:Int,
    @SerializedName("role")val role:String,
    @SerializedName("present")val present:Boolean,
    @SerializedName("createdDate")val createdDate:String,
    @SerializedName("modifiedDate")val modifiedDate:String
)

data class CategoryEntity(
    @SerializedName("categoryId")val categoryId:Int,
    @SerializedName("categoryName")val categoryName:String
)