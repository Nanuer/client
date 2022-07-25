package com.example.nanuer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentFindPwStep1Binding

class FindPwStep1Fragment : Fragment() {

    lateinit var binding: FragmentFindPwStep1Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindPwStep1Binding.inflate(inflater,container,false)
        return binding.root
    }
}