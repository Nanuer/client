package com.example.nanuer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nanuer.databinding.ListItemBinding

class ListRVAdapter(private val postList: ArrayList<Post> ): RecyclerView.Adapter<ListRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(post: Post)
//        fun onRemoveAlbum(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

//    fun addItem(list: List){
//        postList.add(list)
//        notifyDataSetChanged()
//    }
//
//    fun removeItem(position: Int){
//        postList.removeAt(position)
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListRVAdapter.ViewHolder {
        val binding: ListItemBinding = ListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListRVAdapter.ViewHolder, position: Int) {
        holder.bind(postList[position])
        holder.itemView.setOnClickListener{mItemClickListener.onItemClick(postList[position])}
    }

    override fun getItemCount(): Int = postList.size

    inner class ViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post){
            binding.listPostTitleTv.text = post.title
            binding.listPostMinuteTv.text = post.time
            binding.listPostCategoryTv.text = post.menu
        }

    }
}


