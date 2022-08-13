package com.example.nanuer

interface GetUserIdView {
    fun onGetUserIdSuccess(result: String)
    fun onGetUserIdFailure(code:Int, msg:String)
}