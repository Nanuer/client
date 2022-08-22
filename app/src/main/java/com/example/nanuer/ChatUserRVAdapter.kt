package com.example.nanuer

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nanuer.databinding.ChatUserItemBinding
import com.example.nanuer.databinding.ListItemBinding

class ChatUserRVAdapter(private val userId: Int, private val userList: ArrayList<ChatUser>): RecyclerView.Adapter<ChatUserRVAdapter.ViewHolder>(){
    interface MyItemClickListener{
        fun onItemClick(chat: Chat)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun addItem(user:ChatUser){
        userList.add(user)
//        Log.d("TEST","1.${chat}, 2.${userList}, 3.${userList.size}")
        notifyDataSetChanged()
    }

    fun removeItem(userId: Int){
        for(i in 0 until userList.size){
            if(userList[i].userId==userId){
                userList.removeAt(i)
                break
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ChatUserRVAdapter.ViewHolder {
        val binding: ChatUserItemBinding = ChatUserItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatUserRVAdapter.ViewHolder, position: Int) {
        holder.bind(userList[position])
        holder.itemView.setOnClickListener {
            if(userList[position].selected==false){
                holder.itemView.setBackgroundResource(R.color.gray_dark_1)
                userList[position].selected=true
            }else{
                holder.itemView.setBackgroundResource(R.color.gray_light_1)
                userList[position].selected=false
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = userList.size

    inner class ViewHolder(val binding: ChatUserItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: ChatUser){
            binding.chatUserItemUsernameTv.text = user.ninkName
            binding.chatUserItemUsernameTv.text = user.userId.toString()
//            binding.chatUserItemProfileIv.setImageResource()
        }
    }
}