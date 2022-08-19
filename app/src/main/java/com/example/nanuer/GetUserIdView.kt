package com.example.nanuer

interface GetUserIdView {
    fun onGetUserIdSuccess(userId: Int)
    fun onGetUserIdFailure(code:Int, msg:String)
}