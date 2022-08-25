package com.example.nanuer

interface GetProgressView {
    fun onGetProgressSuccess(progress:String)
    fun onGetProgressFailure(code:Int, msg:String)
}