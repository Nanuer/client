package com.example.nanuer

interface UpdatePwView {
    fun onUpdatePwSuccess(result: UserPw)
    fun onUpdatePwFailure(code:Int, msg:String)
}