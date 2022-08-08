package com.example.nanuer

interface GetPostsView {
    fun onGetPostsSuccess(result:PostResult)
    fun onGetPostsFailure(code:Int, msg:String)
}