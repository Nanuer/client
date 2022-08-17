package com.example.nanuer

interface GetMyPostsView {
    fun onGetMyPostsSuccess(result:PostResult)
    fun onGetMyPostsFailure(code:Int, msg:String)
}