package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentFindPwStep2Binding

class FindPwStep2Fragment : Fragment(){
    lateinit var binding: FragmentFindPwStep2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindPwStep2Binding.inflate(inflater,container,false)

        val intent = Intent(getActivity(), LoginActivity::class.java)

        binding.findIdStep2LoginBtn.setOnClickListener {
            startActivity(intent)
        }

        return binding.root
    }
}