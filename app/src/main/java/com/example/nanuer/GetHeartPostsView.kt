package com.example.nanuer

interface GetHeartPostsView {
    fun onGetHeartPostsSuccess(result:PostResult)
    fun onGetHeartPostsFailure(code:Int, msg:String)
}