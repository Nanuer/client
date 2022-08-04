package com.example.nanuer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentPostBinding

class PostFragment : Fragment(){
    lateinit var binding: FragmentPostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater,container,false)

        binding.postBackIv.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main_fl, ListFragment())
                addToBackStack(null)
                commit()
            }
        }
        return binding.root
    }
}