package com.example.nanuer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentSearchBinding
import com.google.gson.Gson

class SearchFragment: Fragment() {
    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        val university = arguments?.getString("university")

        binding.searchBackIv.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fl, ListFragment().apply{
                    arguments = Bundle().apply {
                        putString("university", university)
                    }
                })
                .commit()
        }

        binding.searchOkTv.setOnClickListener {
//            binding.searchTestTv.text = binding.searchEt.text
//            binding.searchEt.text = null
            val text = binding.searchEt.text.toString()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fl, ListFragment().apply {
                    arguments = Bundle().apply {
                        val gson = Gson()
                        val searchJason = gson.toJson(text)
                        putString("searchData", searchJason)
                        putString("university", university)
                    }
                }).commit()
        }

        return binding.root
    }
}