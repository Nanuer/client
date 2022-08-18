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
//    private var listdatas = ArrayList<Post2>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)


//임시 데이터 API연결 안할 때 임시 사용
//        listdatas.apply {
//            add(Post2(title="서브웨이 시키실분",content = "오늘 오후 6시에 학산소극장에서 서브웨이 배달시킬분 구합니다 최소금액은 15000원이라 7000원 이상 주문하셔야해요 배달비는 3000원이고 거래는 계좌이체로 진행합니다",menu="브런치",time = "오늘 오후 7시"))
//            add(Post2(title="맥도날드 시키실분",content ="오늘 오후 6시에 학산소극장에서 맥도날드 배달시킬분 구합니다 최소금액은 15000원이라 7000원 이상 주문하셔야해요 배달비는 3000원이고 거래는 계좌이체로 진행합니다",menu="햄버거",time = "오늘 오후 5시"))
//            add(Post2(title="버거킹 시키실분",content ="오늘 오후 6시에 학산소극장에서 버거킹 배달시킬분 구합니다 최소금액은 15000원이라 7000원 이상 주문하셔야해요 배달비는 3000원이고 거래는 계좌이체로 진행합니다",menu="햄버거",time = "오늘 오후 1시"))
//            add(Post2(title="플랜비 시키실분",content ="오늘 오후 6시에 학산소극장에서 플랜비 배달시킬분 구합니다 최소금액은 15000원이라 7000원 이상 주문하셔야해요 배달비는 3000원이고 거래는 계좌이체로 진행합니다",menu="부리또",time = "오늘 오후 3시"))
//            add(Post2(title="BBQ 시키실분",content ="오늘 오후 6시에 학산소극장에서 BBQ 배달시킬분 구합니다 최소금액은 15000원이라 7000원 이상 주문하셔야해요 배달비는 3000원이고 거래는 계좌이체로 진행합니다",menu="치킨",time = "오늘 오전 12시"))
//        }
//
//        val listRVAdapter = ListRVAdapter(listdatas)
//        binding.listRv.adapter = listRVAdapter
//        binding.listRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
//
//        listRVAdapter.setMyItemClickListener(object: ListRVAdapter.MyItemClickListener{
//            override fun onItemClick(post: Post2) {
//                    changePostFragment(post)
//                }
//            })


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
        return spf!!.getString("Jwt","0")
    }

    private fun getPosts(){
//        val postService = PostService()
//        postService.setGetPostsView(this)
//        postService.getPosts()

        // 대학별 post 갖고오기 + 검색어로 결과 가져오기
        val jwt = getJwt()
        val postService = PostService()
        postService.setGetPostsView(this)

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
    }

    override fun onGetPostsFailure(code: Int, msg: String) {

    }

}