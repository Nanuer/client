package com.example.nanuer

interface GetUserInfoView {
    fun onGetUserInfoSuccess(userInfo: User)
    fun onGetUserInfoFailure(code:Int, msg:String)
}