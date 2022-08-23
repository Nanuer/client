package com.example.nanuer

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nanuer.databinding.FragmentListBinding
import com.google.gson.Gson

class ListFragment : Fragment(),GetPostsView{
    private lateinit var binding: FragmentListBinding
    private lateinit var listRVAdapter: ListRVAdapter
    private var categoryId = 1
    private var gson: Gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)

        val university = arguments?.getString("university")
        binding.listUniversityNameTv.text = university

        handleCategoryBtns()

        binding.listSearchIv.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_fl, SearchFragment())
                .commitAllowingStateLoss()
        }

        binding.listAddPostIv.setOnClickListener{
            startActivity(Intent(requireActivity(),MakePostActivity::class.java))
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getPosts()
    }

    private fun handleCategoryBtns(){
        val category1 = binding.listCategory1Tv
        val category2 = binding.listCategory2Tv
        val category3 = binding.listCategory3Tv
        val category4 = binding.listCategory4Tv
        val category5 = binding.listCategory5Tv

        category1.setTextColor(Color.parseColor("#000000"))
        category1.setBackgroundResource(R.drawable.textline_bottom_gray_bold)

        val categories = arrayListOf<TextView>(category1,category2,category3,category4,category5)

        for(i:Int in 0..4){
            categories[i].setOnClickListener {
                for(j:Int in 0..4){
                    if(j==i) {
                        categories[j].setTextColor(Color.parseColor("#000000"))
                        categories[j].setBackgroundResource(R.drawable.textline_bottom_gray_bold)
                    } else{
                        categories[j].setTextColor(Color.parseColor("#767676"))
                        categories[j].setBackgroundResource(android.R.color.transparent)
                    }
                }
                categoryId=i+1
                getPosts()
            }

        }
    }


    private fun getJwt():String?{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("jwt","0")
    }

    private fun getPosts(){
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
            postService.getPostsByUnivAndQuery(jwt!!, "")
        }
    }

    private fun initRecyclerView(postList:ArrayList<Post2>){
        listRVAdapter = ListRVAdapter(requireContext(), postList)
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
        val selectedList = ArrayList<Post2>()
        for(post in result.postList){
            if(post.categoryEntity?.categoryId == categoryId){
                selectedList.add(post)
            }
        }
        initRecyclerView(selectedList)
    }

    override fun onGetPostsFailure(code: Int, msg: String) {

    }

}