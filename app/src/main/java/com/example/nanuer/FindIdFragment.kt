package com.example.nanuer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentFindIdBinding

class FindIdFragment : Fragment() {

    lateinit var binding: FragmentFindIdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindIdBinding.inflate(inflater, container, false)

        childFragmentManager.beginTransaction().replace(R.id.find_id_fl, FindIdStep1Fragment()).commit()

        return binding.root
    }
}