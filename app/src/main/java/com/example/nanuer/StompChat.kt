package com.example.nanuer

import android.util.Log
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient

class StompChat {
    lateinit var stompConnection: Disposable
    lateinit var topic: Disposable
    val intervalMillis = 1000L
    val client = OkHttpClient()
    val stomp = StompClient(client, intervalMillis)

    // CONNECT
    fun connect(){
        stomp.url = CHAT_URL
        stompConnection = stomp.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {

                }
                Event.Type.CLOSED -> {

                }
                Event.Type.ERROR -> {

                }
            }
        }
    }

    fun subscribe(){
        topic = stomp.join("/destination")
            .doOnError{error -> }
            .subscribe { Log.i("AA", it) }
    }
    fun unsubscribe(){
        topic.dispose()
    }

    fun send(){
        stomp.send("/app/[destination]", "[MESSAGE]").subscribe {
            if(it){

            }
        }
    }

    fun disconnect(){
        stompConnection.dispose()
    }

}