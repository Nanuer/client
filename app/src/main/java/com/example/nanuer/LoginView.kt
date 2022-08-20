package com.example.nanuer

interface LoginView {
    fun onLoginSuccess(code:Int, jwt:String, university:String)
    fun onLoginFailure(code:Int, msg:String)
}