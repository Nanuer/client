package com.example.nanuer

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nanuer.databinding.ChatCenterItemBinding
import com.example.nanuer.databinding.ChatLeftItemBinding
import com.example.nanuer.databinding.ChatRightItemBinding

class ChatRVAdapter(private val userId: Int, private val chatList: ArrayList<Chat>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    interface MyItemClickListener{
        fun onItemClick(chat: Chat)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun addItem(chat:Chat){
        chatList.add(chat)
//        Log.d("TEST","1.${chat}, 2.${chatList}, 3.${chatList.size}")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 0){ //center
            val binding: ChatCenterItemBinding = ChatCenterItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return CenterViewHolder(binding)
        }else if(viewType ==1){ // left
            val binding: ChatLeftItemBinding = ChatLeftItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return LeftViewHolder(binding)
        }else{ // right
            val binding: ChatRightItemBinding = ChatRightItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return RightViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].type != "TALK") 0
                else if(userId == chatList[position].userId) 2
                else 1
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CenterViewHolder){
//            (holder as CenterViewHolder).msg.text = chatList[position].msg
            (holder as CenterViewHolder).msg.text = "누군가가 들어가거나 나갔습니다."
        }else if(holder is LeftViewHolder){
            (holder as LeftViewHolder).userName.text = chatList[position].userId.toString()
            (holder as LeftViewHolder).msg.text = chatList[position].msg
        }else{
            (holder as RightViewHolder).userName.text = chatList[position].userId.toString()
            (holder as RightViewHolder).msg.text = chatList[position].msg
        }
    }

    override fun getItemCount(): Int = chatList.size

    inner class RightViewHolder(val binding: ChatRightItemBinding): RecyclerView.ViewHolder(binding.root){
        val userName = binding.chatRightItemUsernameTv
        val msg = binding.chatRightItemChattingTv
    }

    inner class LeftViewHolder(val binding: ChatLeftItemBinding): RecyclerView.ViewHolder(binding.root){
        val userName = binding.chatLeftItemUsernameTv
        val msg = binding.chatLeftItemChattingTv
    }

    inner class CenterViewHolder(val binding: ChatCenterItemBinding): RecyclerView.ViewHolder(binding.root){
        val msg = binding.chatCenterItemChattingTv
        // type message를 가져와야함
    }
}