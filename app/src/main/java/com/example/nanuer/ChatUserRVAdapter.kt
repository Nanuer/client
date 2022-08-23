package com.example.nanuer

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nanuer.databinding.ChatUserItemBinding
import com.example.nanuer.databinding.ListItemBinding

class ChatUserRVAdapter(private val context: Context, private val userList: ArrayList<ChatUser>): RecyclerView.Adapter<ChatUserRVAdapter.ViewHolder>(){
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

    fun isSelected(userId: Int):Boolean{
        for(i in 0 until userList.size){
            Log.d("lalala","lalal")
            if(userList[i].userId==userId){
                Log.d("FINDUSER","SELECTED")
                return userList[i].selected
            }
        }
        return false
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ChatUserRVAdapter.ViewHolder {
        val binding: ChatUserItemBinding = ChatUserItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatUserRVAdapter.ViewHolder, position: Int) {
        holder.bind(userList[position])
        holder.itemView.setOnClickListener {
            if(!userList[position].selected){
                holder.itemView.setBackgroundResource(R.drawable.chat_user_item_selected)
                userList[position].selected=true
                notifyItemChanged(position)
            }else{
                holder.itemView.setBackgroundResource(R.drawable.chat_user_item_unselected)
                userList[position].selected=false
                notifyItemChanged(position)
            }
            Log.d("userList",userList.toString())
        }
    }

    override fun getItemCount(): Int = userList.size

    inner class ViewHolder(val binding: ChatUserItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: ChatUser){
            binding.chatUserItemUsernameTv.text = user.ninkName

            Glide.with(context).load(user.profileImg).circleCrop().into(binding.chatUserItemProfileIv);
//            binding.chatUserItemProfileIv.setImageResource(user.profileImg)
        }
    }
}