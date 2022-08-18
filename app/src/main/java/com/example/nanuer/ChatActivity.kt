package com.example.nanuer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivityChatBinding
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import org.json.JSONObject

class ChatActivity: AppCompatActivity(), GetRoomAndUserIdView{
    lateinit var binding: ActivityChatBinding
    lateinit var stompConnection: Disposable
    lateinit var topic: Disposable
    private val intervalMillis = 1000L
    private val client = OkHttpClient()
    private val stomp = StompClient(client, intervalMillis)

    private var userId:Int = -1
    private var roomId:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getRoomAndUserId()

        stomp.url = CHAT_URL
        stompConnection = stomp.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {
                    Log.d("hello", "Hello1")
                }
                Event.Type.CLOSED -> {
                    Log.d("hello", "Hello2")
                }
                Event.Type.ERROR -> {
                    Log.d("hello", "Hello3")
                }
            }
        }

        topic = stomp.join("/sub/channel/")
            .doOnError{error -> Log.d("ERROR","subscribe error")}
            .subscribe { Log.i("AA", it) }

        send("ENTER",roomId,userId,"")


        binding.chatPersonalBackIv.setOnClickListener{
            finish()
        }

        binding.chatPersonalSendBtn.setOnClickListener{
            val msg = binding.chatPersonalWriteEt.text.toString()
            send("TALK",roomId,userId,msg)
        }
    }

    private fun send(type:String, roomId:Int, sender:Int, data:String){
        val jsonObject = JSONObject()
        jsonObject.put("type",type)
        jsonObject.put("channelId",roomId)
        jsonObject.put("sender",sender)
        jsonObject.put("data",data)
        val jsonString = jsonObject.toString()

        stomp.send("topic", jsonString).subscribe {
            if(it){

            }
        }
    }

    private fun getRoomAndUserId(){
//        val postId = intent.getIntExtra("postId",0)
        val chatService = ChatService()
        chatService.setGetRoomIdAndUserIdView(this)
//        chatService.getRoomIdAndUserId(postId)
        chatService.getRoomIdAndUserId(6)
    }

    override fun onDestroy() {
        super.onDestroy()
        send("QUIT",roomId,userId,"")
        topic.dispose()
        stompConnection.dispose()
    }

    override fun onGetRoomAndUserIdSuccess(result: GetRoomAndUserIdResult) {
        userId = result.userId
        roomId = result.roomId
        Log.d("roomId UserID", "${userId}, ${roomId}")
    }

    override fun onGetRoomAndUserIdFailure(code: Int, msg: String) {

    }
}

