package com.example.nanuer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    var step = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment1 = SingUpStep1Fragment()
        fragmentTransaction.add(R.id.singup_fragment_container, fragment1)
        fragmentTransaction.commit()

        binding.singupBackIv.setOnClickListener{
            when(step){
                1 -> finish()
            }
        }
    }
}