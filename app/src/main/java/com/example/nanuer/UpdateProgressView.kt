package com.example.nanuer

interface UpdateProgressView {
    fun onUpdateProgressSuccess()
    fun onUpdateProgressFailure(code:Int, msg:String)
}