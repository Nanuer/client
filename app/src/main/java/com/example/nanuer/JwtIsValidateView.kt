package com.example.nanuer

interface JwtIsValidateView {
    fun onJwtIsValidateSuccess(result: Boolean)
    fun onJwtIsValidateFailure(code:Int, msg:String)
}