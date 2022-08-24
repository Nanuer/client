package com.example.nanuer

interface UpdatePwView {

    fun onUpdatePwSuccess(result: String)
    fun onUpdatePwFailure(code:Int, msg:String)
}