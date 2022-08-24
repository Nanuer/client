package com.example.nanuer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivityAccountBinding

class AccountActivity :AppCompatActivity(){
    lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val account = intent.getStringExtra("account")
        val costInfoPerOne = intent.getIntExtra("costInfoPerOne",0)
        val deliveryCostPerOne = intent.getIntExtra("deliveryCostPerOne",0)
        val categoryId = intent.getIntExtra("categoryId",0)

        if(categoryId==1){
            binding.accountAccountTv.text="${account}\n로 (본인이 주문 하신 메뉴의 금액 + ${deliveryCostPerOne})원을 보내주세요."
        }else{
            binding.accountAccountTv.text="${account}\n로 ${costInfoPerOne}원을 보내주세요."
        }

        binding.accountOkBtn.setOnClickListener {
            finish()
        }
    }
}