package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentFindIdStep1Binding

class FindIdStep1Fragment : Fragment() {

    lateinit var binding: FragmentFindIdStep1Binding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindIdStep1Binding.inflate(inflater, container, false)

        return binding.root

    }





}