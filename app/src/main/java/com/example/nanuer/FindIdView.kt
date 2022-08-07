package com.example.nanuer

interface FindIdView {
    fun onFindIdSuccess(code:Int, result: FindIdResult)
    fun onFindIdFailure(code:Int, msg:String)
}