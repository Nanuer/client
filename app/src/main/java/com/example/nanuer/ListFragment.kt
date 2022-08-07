package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nanuer.databinding.FragmentListBinding
import com.google.gson.Gson

class ListFragment : Fragment(){
    lateinit var binding: FragmentListBinding
    private var listdatas = ArrayList<Post>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)


//임시 데이터
        listdatas.apply {
            add(Post("서브웨이 시키실분","오늘 오후 6시에 학산소극장에서 서브웨이 배달시킬분 구합니다 최소금액은 15000원이라 7000원 이상 주문하셔야해요 배달비는 3000원이고 거래는 계좌이체로 진행합니다",menu="브런치",time = "오늘 오후 7시"))
            add(Post("맥도날드 시키실분","오늘 오후 6시에 학산소극장에서 맥도날드 배달시킬분 구합니다 최소금액은 15000원이라 7000원 이상 주문하셔야해요 배달비는 3000원이고 거래는 계좌이체로 진행합니다",menu="햄버거",time = "오늘 오후 5시"))
            add(Post("버거킹 시키실분","오늘 오후 6시에 학산소극장에서 버거킹 배달시킬분 구합니다 최소금액은 15000원이라 7000원 이상 주문하셔야해요 배달비는 3000원이고 거래는 계좌이체로 진행합니다",menu="햄버거",time = "오늘 오후 1시"))
            add(Post("플랜비 시키실분","오늘 오후 6시에 학산소극장에서 플랜비 배달시킬분 구합니다 최소금액은 15000원이라 7000원 이상 주문하셔야해요 배달비는 3000원이고 거래는 계좌이체로 진행합니다",menu="부리또",time = "오늘 오후 3시"))
            add(Post("BBQ 시키실분","오늘 오후 6시에 학산소극장에서 BBQ 배달시킬분 구합니다 최소금액은 15000원이라 7000원 이상 주문하셔야해요 배달비는 3000원이고 거래는 계좌이체로 진행합니다",menu="치킨",time = "오늘 오전 12시"))
        }

        val listRVAdapter = ListRVAdapter(listdatas)
        binding.listRv.adapter = listRVAdapter
        binding.listRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        listRVAdapter.setMyItemClickListener(object: ListRVAdapter.MyItemClickListener{
            override fun onItemClick(post: Post) {
                    changePostFragment(post)
                }
            })

        return binding.root
    }

    private fun changePostFragment(post: Post){
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

}