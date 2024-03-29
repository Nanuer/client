package com.example.nanuer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nanuer.databinding.ListItemBinding

class ListRVAdapter(val context: Context, val postList: ArrayList<Post2>): RecyclerView.Adapter<ListRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(post: Post2)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListRVAdapter.ViewHolder {
        val binding: ListItemBinding = ListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListRVAdapter.ViewHolder, position: Int) {
        holder.bind(postList[position])
        holder.itemView.setOnClickListener{mItemClickListener.onItemClick(postList[position])}
        holder.binding.listPostHeartOnTv.setOnClickListener{
            holder.binding.listPostHeartOnTv.visibility = View.GONE
            holder.binding.listPostHeartOffTv.visibility = View.VISIBLE
        }
        holder.binding.listPostHeartOffTv.setOnClickListener{
            holder.binding.listPostHeartOnTv.visibility = View.VISIBLE
            holder.binding.listPostHeartOffTv.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int = postList.size

    inner class ViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post2){
            binding.listPostTitleTv.text = post.title
            if(post.time!="시간설정"&&post.time!=null)binding.listPostTimeTv.text = post.time

            if(post.progress=="Recruit"){
                binding.listPostRecruitTv.visibility=View.VISIBLE
                binding.listPostRecruitSuccessTv.visibility=View.GONE
            }else{
                binding.listPostRecruitTv.visibility=View.GONE
                binding.listPostRecruitSuccessTv.visibility=View.VISIBLE
            }

            val createdDate = post.created_date
            val date = createdDate?.substring(0,10)
            val time = createdDate?.substring(11,16)
            binding.listPostMinuteTv.text = "${date} ${time}"
        }
    }
}


