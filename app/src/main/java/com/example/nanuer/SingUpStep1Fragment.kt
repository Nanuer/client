package com.example.nanuer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentSignupStep1Binding

class SingUpStep1Fragment : Fragment(){

    lateinit var binding: FragmentSignupStep1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupStep1Binding.inflate(inflater, container, false)

        return binding.root
    }
}