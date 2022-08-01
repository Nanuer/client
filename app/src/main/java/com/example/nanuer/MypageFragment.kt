package com.example.nanuer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentMypageBinding

class MypageFragment : Fragment(){
    lateinit var binding: FragmentMypageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater,container,false)

        binding.mypageLogoutTv.setOnClickListener {
            // 임시버튼 누르고 mainActivity 진입 시 jwt가 저장이 안 되기 때문에 에러 방지용으로 주석처리함
//            logout()
            activity?.finish()
        }
        return binding.root
    }

    private fun logout(){
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("jwt")
        editor.apply()
    }
}