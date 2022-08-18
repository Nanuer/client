package com.example.nanuer

interface GetRoomAndUserIdView {
    fun onGetRoomAndUserIdSuccess(result:GetRoomAndUserIdResult)
    fun onGetRoomAndUserIdFailure(code:Int, msg:String)
}