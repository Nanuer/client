package com.example.nanuer

interface FindIdView {
    fun onFindIdSuccess(code:Int, jwt:String)
    fun onFindIdFailure(code:Int, msg:String)
}