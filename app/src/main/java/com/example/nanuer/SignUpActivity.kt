package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    private val fragmentArr = arrayOf(SingUpStep1Fragment(), SingUpStep2Fragment(), SingUpStep3Fragment(), SingUpStep4Fragment())
    private var step = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .add(R.id.singup_fragment_container, fragmentArr[0]).addToBackStack(null).commit()

        binding.singupBackIv.setOnClickListener{
            val fragmentTransaction = fragmentManager.beginTransaction()
            val frameLayout = fragmentManager.findFragmentById(R.id.singup_fragment_container)
            when(step){
                1 -> finish()
                2,3,4 -> fragmentTransaction.remove(frameLayout!!).commit()
            }
            step--
            handleStepImg()
        }

        binding.signupNextBtn.setOnClickListener {
            val fragmentTransaction = fragmentManager.beginTransaction()
            when(step){
                1,2,3->fragmentTransaction.add(R.id.singup_fragment_container, fragmentArr[step]).addToBackStack(null).commit()
                4->startActivity(Intent(this, MainActivity::class.java))
            }
            step++
            handleStepImg()
        }
    }
    private fun handleStepImg(){
        when(step){
            1->binding.singupStepIv.setImageResource(R.drawable.step_first)
            2->binding.singupStepIv.setImageResource(R.drawable.step_second)
            3->binding.singupStepIv.setImageResource(R.drawable.step_third)
            4->binding.singupStepIv.setImageResource(R.drawable.step_fourth)
        }
    }
}