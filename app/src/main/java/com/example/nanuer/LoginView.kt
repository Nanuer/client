package com.example.nanuer

interface LoginView {
    fun onLoginSuccess(code:Int, jwt:String)
    fun onLoginFailure()
}