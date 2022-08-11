package com.example.nanuer

interface DeletePostView {
    fun onDeletePostSuccess()
    fun onDeletePostFailure(code:Int, msg:String)
}