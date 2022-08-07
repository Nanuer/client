package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentListBinding

class ListFragment : Fragment(){
    lateinit var binding: FragmentListBinding
    private var listdatas =ArrayList<List>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)

//        binding.listPostTitleTv.setOnClickListener {
//            parentFragmentManager.beginTransaction().apply {
//                replace(R.id.main_fl, PostFragment())
//                addToBackStack(null)
//                commit()
//            }
//        }

//임시 데이터
        listdatas.apply {
            add(List("서브웨이 시키실분"))
        }

        val listRVAdapter = ListRVAdapter(listdatas)
        binding.listRv.adapter = listRVAdapter

        listRVAdapter.setMyItemClickListener(object: ListRVAdapter.MyItemClickListener{
            override fun onItemClick(list: List) {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.main_fl, PostFragment())
                    addToBackStack(null)
                    commit()
                }

            }

            override fun onRemoveAlbum(position: Int) {
                TODO("Not yet implemented")
            }
        })





        return binding.root
    }

}