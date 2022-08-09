package com.example.nanuer

interface FindIdView {
    fun onFindIdSuccess(result: FindIdResult)
    fun onFindIdFailure(code:Int, msg:String)
}