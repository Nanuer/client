package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentFindIdStep2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindIdStep2Fragment : Fragment() {
    lateinit var binding : FragmentFindIdStep2Binding
    var userEmail = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindIdStep2Binding.inflate(inflater, container, false)



        binding.findIdStep2LoginBtn.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return binding.root

    }

        private fun getEmail(email:String){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.getEmail(email).enqueue(object: Callback<GetEmailResponse> {
            override fun onResponse(call: Call<GetEmailResponse>, response: Response<GetEmailResponse>
            ) {
                Log.d("CODE/SUCCESS", response.toString())
                val resp: GetEmailResponse = response.body()!!
                Log.d("CODE/SUCCESS", resp.toString())
                when(resp.code){
                    1000->{
                        userEmail=resp.result
                        binding.findIdStep2UserIdTv.text = userEmail
                    }
                    else -> {}
                }
            }
            override fun onFailure(call: Call<GetEmailResponse>, t: Throwable) {
                Log.d("GET/CODE/FAILURE", t.message.toString())
            }
        })
    }




}