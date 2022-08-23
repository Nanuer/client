package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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
    private lateinit var chatUserRVAdapter: ChatUserRVAdapter
    lateinit var stompConnection: Disposable
    lateinit var topic: Disposable
    private val intervalMillis = 1000L
    private val client = OkHttpClient()
    private val stomp = StompClient(client, intervalMillis)
    private var chatDataList = ArrayList<Chat>()
    private var userList = ArrayList<ChatUser>()
    private var userId:Int = -1
    private var roomId:Int = -1
    private var nickname:String = "미정"
    private var profileImg:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imm=getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager

        getRoomAndUserId()

        binding.chatPersonalBackIv.setOnClickListener{
            finish()
        }

        binding.chatPersonalSendBtn.setOnClickListener{
            val msg = binding.chatPersonalWriteEt.text.toString()
            send("TALK",roomId,userId,msg,nickname,profileImg)
            if(binding.chatPersonalWriteEt.length() > 0){
                binding.chatPersonalWriteEt.text.clear()
            }
        }

        binding.chatDealOkBtn.setOnClickListener {
            handleAccountDialog()
        }
    }

    private fun setChatTitleHeaderMessage(userId:Int,bossUserId:Int){
        val title = intent.getStringExtra("title")
        binding.chatPersonalTitleTv.text = title
        if(bossUserId==userId){
            binding.chatDealRl.visibility = View.VISIBLE
            binding.chatPersonalMessageTv.text = "‘${title}’ 채팅 방입니다.\n거래가 확정되면 아래 거래확정 버튼을 눌러주세요"
        }else{
            binding.chatPersonalMessageTv.text = "‘${title}’ 채팅 방입니다.\n방장이 거래확정 버튼을 누르면 계좌번호 창으로 이동합니다."
        }
    }

    private fun hideKeyboard(){
        imm?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun send(type:String, roomId:Int, sender:Int, data:String, nickname:String, profileImg:String?){
        val jsonObject = JSONObject()
        jsonObject.put("type",type)
        jsonObject.put("roomId",roomId)
        jsonObject.put("sender",sender)
        jsonObject.put("data",data)
        jsonObject.put("nickName",nickname)
        jsonObject.put("profileImg",profileImg)
        val jsonString = jsonObject.toString()
        stomp.send("/pub/send", jsonString).subscribe {
            if(it){ }
        }
    }

    private fun getRoomAndUserId(){
        val jwt = getJwt()
        val postId = intent.getIntExtra("postId",0)
        val chatService = ChatService()
        chatService.setGetRoomIdAndUserIdView(this)
        chatService.getRoomIdAndUserId(jwt!!, postId)
//        chatService.getRoomIdAndUserId(jwt!!, 6)
    }

    private fun getJwt():String?{
        val spf = this.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("jwt","0")
    }

    private fun handleAccountDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_account, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        val mAlertDialog = mBuilder.show()

        val accountEt = mDialogView.findViewById<EditText>(R.id.dialog_account_et)
        val registerBtn = mDialogView.findViewById<TextView>(R.id.dialog_account_tv)

        registerBtn.setOnClickListener {
            val account = accountEt.text.toString()
            send("QUIT",roomId,userId,"CONFIRM${account}",nickname,profileImg)
            mAlertDialog.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        send("QUIT",roomId,userId,"${nickname}님이 나갔습니다.",nickname,profileImg)
        topic.dispose()
        stompConnection.dispose()
    }

    override fun onGetRoomAndUserIdSuccess(result: GetRoomAndUserIdResult) {
        roomId = result.roomNumber
        userId = result.userId
        nickname = result.nickName

        val bossUserId = intent.getIntExtra("userId",0)
        setChatTitleHeaderMessage(userId,bossUserId)

        chatRVAdapter = ChatRVAdapter(this, userId, chatDataList)
        binding.chatPersonalRv.adapter = chatRVAdapter
        binding.chatPersonalRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            .apply {
                this.stackFromEnd = true	// 가장 최근의 대화를 표시하기 위해 맨 아래로 정렬.
            }

        chatUserRVAdapter = ChatUserRVAdapter(this, userList)
        binding.chatUsersRv.adapter = chatUserRVAdapter
        binding.chatUsersRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        stomp.url = CHAT_URL
        stompConnection = stomp.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {
                    Log.d("CONNECT", "OPENED")
                }
                Event.Type.CLOSED -> {
                    Log.d("CONNECT", "CLOSED")
                }
                Event.Type.ERROR -> {
                    Log.d("CONNECT", "ERROR")
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
                val nickname = chatObject.getString("nickName")
                val profileImg = chatObject.getString("profileImg")
                Log.d("CHAT DATA", chatData.toString())
                Log.d("MSG, USERID", "${data}, ${userId}")

                if(type=="ENTER"&&bossUserId!=userId){
                    runOnUiThread { chatUserRVAdapter.addItem(ChatUser(userId=userId,nickName=nickname,profileImg=profileImg)) }
                }else if(type=="QUIT"){
                    if(data.substring(0 until 7)=="CONFIRM"&&bossUserId==userId){
                        val intent = Intent(this,AccountActivity::class.java)
                        intent.putExtra("account",data.substring(7))
                        startActivity(intent)
//                        Log.d("AAAA",chatUserRVAdapter.isSelected(userId).toString())
//                        if(chatUserRVAdapter.isSelected(userId)){
//                            val intent = Intent(this,AccountActivity::class.java)
//                            intent.putExtra("account",data.substring(7))
//                            startActivity(intent)
//                        }else{
//                            finish()
//                        }
                    }else{
                        runOnUiThread{
                            chatUserRVAdapter.removeItem(userId)
                        }
                    }
                }

                runOnUiThread {
                    chatRVAdapter.addItem(Chat(userId=userId,type=type,msg=data,nickName=nickname,profileImg=profileImg))
                }
                hideKeyboard()
                Log.d("LIST", chatDataList.toString())
            }
        Log.d("roomId UserID", "${userId}, ${roomId}")

        send("ENTER",roomId,userId,"${nickname}님이 들어왔습니다.",nickname,profileImg)
    }

    override fun onGetRoomAndUserIdFailure(code: Int, msg: String) {

    }
}

