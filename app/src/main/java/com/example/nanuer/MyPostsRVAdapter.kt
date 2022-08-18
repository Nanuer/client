package com.example.nanuer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nanuer.databinding.ListItemBinding

class MyPostsRVAdapter(val context: Context, val result: PostResult): RecyclerView.Adapter<MyPostsRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(post: Post2)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyPostsRVAdapter.ViewHolder {
        val binding: ListItemBinding = ListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPostsRVAdapter.ViewHolder, position: Int) {
        holder.bind(result.postList[position])
        holder.itemView.setOnClickListener{mItemClickListener.onItemClick(result.postList[position])}
    }

    override fun getItemCount(): Int = result.postList.size

    inner class ViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post2){
            binding.listPostTitleTv.text = post.title
            binding.listPostMinuteTv.text = post.created_date
            binding.listPostTimeTv.text = post.time
        }
    }
}


