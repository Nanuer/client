package com.example.nanuer

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivityChatBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import io.socket.client.Socket


class ChatActivity: AppCompatActivity(){
//    private lateinit var handler: Handler
    lateinit var binding: ActivityChatBinding
    private val mSocket: Socket? = null
    private val username: String? = null
    private val roomNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        client = OkHttpClient()
//
//        val request: Request = Request.Builder()
//            .url(BASE_URL)
//            .build()
//        val listener = WebSocketListener()
//
//        client.newWebSocket(request, listener)
//        client.dispatcher.executorService.shutdown()
    }




//    inner class ClientThread : Thread() {
//        override fun run(){
//            val host = "43.200.128.206"
//            val port = 81
//            try{
//                val socket = Socket(host, port)
//                val outstream = ObjectOutputStream(socket.getOutputStream())
//                outstream.writeObject("안녕!")
//                outstream.flush()
//                Log.d("ClientThread", "서버로 보냄")
//
//                val instream = ObjectInputStream(socket.getInputStream())
//                val input: Any = instream.readObject()
//                Log.d("ClientThread", "받은 데이터: $input")
//
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.setText("받은 데이터: "+input);
//                    }
//                }
//
//
//            }catch (){
//
//            }
//        }
//    }

}

