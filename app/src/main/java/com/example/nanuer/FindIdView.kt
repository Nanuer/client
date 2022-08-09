package com.example.nanuer

interface FindIdView {
    fun onFindIdSuccess(code:Int, result: String)
    fun onFindIdFailure(code:Int, msg:String)
}