package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentFindPwStep2Binding
import com.google.gson.Gson

class FindPwStep2Fragment : Fragment(){
    lateinit var binding: FragmentFindPwStep2Binding
    private var gson: Gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindPwStep2Binding.inflate(inflater,container,false)


        val updatePwJson = arguments?.getString("password")
        val userPw = gson.fromJson(updatePwJson, String::class.java)
        setInit(userPw)


        binding.findIdStep2LoginBtn.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
    private fun setInit(password: String){
        binding.test.text = password
    }
}