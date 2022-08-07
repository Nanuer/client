package com.example.nanuer

interface PostView {
    fun onPostSuccess()
    fun onPostFailure(code:Int, msg:String)
}