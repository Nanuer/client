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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)

        binding.listPostTitleTv.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main_fl, PostFragment())
                addToBackStack(null)
                commit()
            }
        }



        return binding.root
    }

}