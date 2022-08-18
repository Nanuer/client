package com.example.nanuer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nanuer.databinding.FragmentMypostsBinding
import com.google.gson.Gson

class MyPostsFragment : Fragment(),GetMyPostsView{
    lateinit var binding: FragmentMypostsBinding
    private lateinit var myPostsRVAdapter: ListRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypostsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getMyPosts()
    }

    private fun getMyPosts(){
        val jwt = getJwt()
        val myPageService = MypageService()
        myPageService.setGetMyPostsView(this)
        myPageService.getMyPosts(jwt!!)
    }

    private fun getJwt():String?{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("Jwt","0")
    }

    private fun initRecyclerView(postList:PostResult){
        myPostsRVAdapter = ListRVAdapter(requireContext(), postList)
        binding.mypostsRv.adapter = myPostsRVAdapter
        binding.mypostsRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        myPostsRVAdapter.setMyItemClickListener(object: ListRVAdapter.MyItemClickListener{
            override fun onItemClick(post: Post2) {
                changePostFragment(post)
            }
        })
    }

    private fun changePostFragment(post: Post2){
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fl, PostFragment().apply {
                arguments = Bundle().apply{
                    val gson = Gson()
                    val postJson = gson.toJson(post)
                    putString("post", postJson)
                }
            })
            .commitAllowingStateLoss()
    }

    override fun onGetMyPostsSuccess(postList: PostResult) {
        initRecyclerView(postList)
    }

    override fun onGetMyPostsFailure(code: Int, msg: String) {

    }
}