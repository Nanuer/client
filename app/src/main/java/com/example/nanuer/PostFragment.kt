package com.example.nanuer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentPostBinding
import com.google.gson.Gson

class PostFragment : Fragment(){
    lateinit var binding: FragmentPostBinding
    private var gson:Gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater,container,false)

        val postJson = arguments?.getString("post")
        val post = gson.fromJson(postJson, Post2::class.java)
        setInit(post)


        binding.postHeaderBackIv.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main_fl, ListFragment())
                commit()
            }
        }
        return binding.root
    }

    private fun setInit(post:Post2){
        binding.postCategoryTv.text = post.menu
        binding.postTitleTv.text = post.title
        binding.postContentTv.text = post.content
        binding.postTimeTv.text = post.time
        binding.postLocationTv.text = post.location
        binding.postCreateTimeTv.text = post.createdDate
        binding.postUserNicknameTv.text = post.userEntity?.nickName
//        binding.postProfileIv.setImageResource(post.userEntity.profileImg)
        binding.postFooterDeliveryFeeTv.text = post.delivery_cost
    }
}