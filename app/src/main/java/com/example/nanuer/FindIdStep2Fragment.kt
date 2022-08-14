package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentFindIdStep2Binding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindIdStep2Fragment : Fragment() {
    lateinit var binding : FragmentFindIdStep2Binding
    private var gson: Gson = Gson()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindIdStep2Binding.inflate(inflater, container, false)

        val findIdJson = arguments?.getString("Email")
        val userEmail = gson.fromJson(findIdJson, String::class.java)
        setInit(userEmail)



        binding.findIdStep2LoginBtn.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return binding.root

    }



    private fun setInit(email: String){
        binding.findIdStep2UserIdTv.text = email
    }






}