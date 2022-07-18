package com.example.nanuer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentSignupStep2Binding

class SingUpStep2Fragment : Fragment(){

    lateinit var binding: FragmentSignupStep2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupStep2Binding.inflate(inflater, container, false)

        return binding.root
    }
}