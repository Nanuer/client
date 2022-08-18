package com.example.nanuer

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName(value="title")val title:String,
    @SerializedName(value="content")val content:String?=null,
    @SerializedName(value="deliveryCost")val deliveryCost:String?=null,
    @SerializedName(value="time")val time:String?=null,
    @SerializedName(value="location")val location:String?=null,
    @SerializedName(value="categoryId")val categoryId:Int?=1,
    @SerializedName(value="costInfo")val costInfo:String?=null,
    @SerializedName(value="menu")val menu:String?=null,
    @SerializedName(value="total")val total:Int?=null,
)
