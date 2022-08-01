package com.example.nanuer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentFindPwBinding

class FindPwFragment : Fragment() {

    lateinit var binding: FragmentFindPwBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindPwBinding.inflate(inflater, container, false)

        childFragmentManager.beginTransaction().replace(R.id.find_pw_fl, FindPwStep1Fragment()).commit()

        return binding.root

    }
}