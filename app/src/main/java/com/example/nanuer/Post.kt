package com.example.nanuer

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName(value="title")val title:String,
    @SerializedName(value="content")val content:String?=null,
    @SerializedName(value="cost_info")val cost_info:String?=null,
    @SerializedName(value="menu")val menu:String?=null,
    @SerializedName(value="total")val total:Int?=null,
    @SerializedName(value="delivery_cost")val delivery_cost:String?=null,
    @SerializedName(value="location")val location:String?=null,
    @SerializedName(value="time")val time:String?=null,
    @SerializedName(value="user_id")val user_id:Int?=3,
    @SerializedName(value="category_id")val category_id:Int?=1
)
