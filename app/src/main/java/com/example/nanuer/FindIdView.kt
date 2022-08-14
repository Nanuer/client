package com.example.nanuer

interface FindIdView {
    fun onFindIdSuccess(result: String)
    fun onFindIdFailure(code:Int, msg:String)
}