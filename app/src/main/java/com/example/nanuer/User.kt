package com.example.nanuer

import com.google.gson.annotations.SerializedName;

data class User(
    @SerializedName(value="email")var email:String,
    @SerializedName(value="password")var password:String,
    @SerializedName(value="name")var name:String? = null,
    @SerializedName(value="nickName")var ninkName:String? = null,
    @SerializedName(value="phone")var phone:String? = null,
    @SerializedName(value="birth")var birth:String? = null,
    @SerializedName(value="profileImg")var profileImg:String? = null,
    @SerializedName(value="university")var university:String? = null
)
