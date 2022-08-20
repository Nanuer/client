package com.example.nanuer

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nanuer.databinding.ActivityChatBinding
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import org.json.JSONObject

class ChatActivity: AppCompatActivity(), GetRoomAndUserIdView{
    var imm :InputMethodManager? = null
    private lateinit var binding: ActivityChatBinding
    private lateinit var chatRVAdapter: ChatRVAdapter
    lateinit var stompConnection: Disposable
    lateinit var topic: Disposable
    private val intervalMillis = 1000L
    private val client = OkHttpClient()
    private val stomp = StompClient(client, intervalMillis)
    private var chatDataList = ArrayList<Chat>()

    private var userId:Int = -1
    private var roomId:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imm=getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager

        setChatTitleHeaderMessage()

        getRoomAndUserId()

        binding.chatPersonalBackIv.setOnClickListener{
            finish()
        }

        binding.chatPersonalSendBtn.setOnClickListener{
            val msg = binding.chatPersonalWriteEt.text.toString()
            send("TALK",roomId,userId,msg)
            if(binding.chatPersonalWriteEt.length() > 0){
                binding.chatPersonalWriteEt.text.clear()
            }
        }
    }

    private fun setChatTitleHeaderMessage(){
        val title = intent.getStringExtra("title")
        binding.chatPersonalTitleTv.text = title
        binding.chatPersonalMessageTv.text = "‘${title}’ 채팅 방입니다.\n거래가 확정되면 아래 거래확정 버튼을 눌러주세요"
    }

    private fun hideKeyboard(){
        imm?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun send(type:String, roomId:Int, sender:Int, data:String){
        val jsonObject = JSONObject()
        jsonObject.put("type",type)
        jsonObject.put("roomId",roomId)
        jsonObject.put("sender",sender)
        jsonObject.put("data",data)
        val jsonString = jsonObject.toString()
        stomp.send("/pub/send", jsonString).subscribe {
            if(it){ }
        }
    }

    private fun getRoomAndUserId(){
        val jwt = getJwt()
//        val postId = intent.getIntExtra("postId",0)
        val chatService = ChatService()
        chatService.setGetRoomIdAndUserIdView(this)
//        chatService.getRoomIdAndUserId(postId)
        chatService.getRoomIdAndUserId(jwt!!, 6)
    }

    private fun getJwt():String?{
        val spf = this.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("jwt","0")
    }

    override fun onDestroy() {
        super.onDestroy()
        send("QUIT",roomId,userId,"")
        topic.dispose()
        stompConnection.dispose()
    }

    override fun onGetRoomAndUserIdSuccess(result: GetRoomAndUserIdResult) {
        roomId = result.roomNumber
        userId = result.userId

        chatRVAdapter = ChatRVAdapter(userId, chatDataList)
        binding.chatPersonalRv.adapter = chatRVAdapter
        binding.chatPersonalRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            .apply {
                this.stackFromEnd = true	// 가장 최근의 대화를 표시하기 위해 맨 아래로 정렬.
            }

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

        topic = stomp.join("/sub/channel/${roomId}")
            .doOnError{error -> Log.d("ERROR","subscribe error")}
            .subscribe {
                    chatData->
                val chatObject = JSONObject(chatData)
                val data = chatObject.getString("data")
                val userId = chatObject.getInt("sender")
                val type = chatObject.getString("type")
                Log.d("HELLO1", chatData.toString())
                Log.d("HELLO2", "${data}, ${userId}")
                Log.d("Adapter", chatRVAdapter.toString())
                runOnUiThread {
                    chatRVAdapter.addItem(Chat(userId=userId,type=type,msg=data))
                }
                hideKeyboard()
                Log.d("LIST", chatDataList.toString())
            }
        Log.d("roomId UserID", "${userId}, ${roomId}")

        send("ENTER",roomId,userId,"")
    }

    override fun onGetRoomAndUserIdFailure(code: Int, msg: String) {

    }
}

