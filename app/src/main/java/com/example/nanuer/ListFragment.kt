package com.example.nanuer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nanuer.databinding.FragmentListBinding
import com.google.gson.Gson

class ListFragment : Fragment(),GetPostsView{
    private lateinit var binding: FragmentListBinding
    private lateinit var listRVAdapter: ListRVAdapter
    private var gson: Gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)

        binding.listSearchIv.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_fl, SearchFragment())
                .commitAllowingStateLoss()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getPosts()
    }

    private fun getJwt():String?{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("jwt","0")
    }

    private fun getPosts(){
//        val postService = PostService()
//        postService.setGetPostsView(this)
//        postService.getPosts()

        // 대학별 post 갖고오기 + 검색어로 결과 가져오기
        val jwt = getJwt()
        val postService = PostService()
        postService.setGetPostsView(this)
        Log.d("jwt", jwt!!)

        val searchJson = arguments?.getString("searchData")
        if(searchJson!=null){
            val searchData = gson.fromJson(searchJson, String::class.java)
            postService.getPostsByUnivAndQuery(jwt!!, searchData)
            Log.d("aa",searchData)
        }else{
            postService.getPostsByUnivAndQuery(jwt!!, null)
        }
    }

    private fun initRecyclerView(result:PostResult){
        listRVAdapter = ListRVAdapter(requireContext(), result)
        binding.listRv.adapter = listRVAdapter
        binding.listRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        listRVAdapter.setMyItemClickListener(object: ListRVAdapter.MyItemClickListener{
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

    override fun onGetPostsSuccess(result: PostResult) {
        Log.d("size",result.postList.size.toString() )
        initRecyclerView(result)
        binding.listNumberTv.text = "총 ${result.postList.size}건"
    }

    override fun onGetPostsFailure(code: Int, msg: String) {

    }

}