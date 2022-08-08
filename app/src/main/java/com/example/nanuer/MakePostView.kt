package com.example.nanuer

interface MakePostView {
    fun onMakePostSuccess()
    fun onMakePostFailure(code:Int, msg:String)
}