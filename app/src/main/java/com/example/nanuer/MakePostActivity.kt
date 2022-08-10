package com.example.nanuer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.nanuer.databinding.ActivityMakePostBinding


class MakePostActivity : AppCompatActivity(), MakePostView {
    lateinit var binding: ActivityMakePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.makePostBackIv.setOnClickListener {
            finish()
        }

        binding.makePostFooterOkTv.setOnClickListener {
            makePost()
        }

        binding.makePostCategoryTv.setOnClickListener {
            handleCategoryDialog()
        }
    }

    private fun handleCategoryDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_category, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        val  mAlertDialog = mBuilder.show()

        val onClickListener = View.OnClickListener { view ->
            val categoryTv = binding.makePostCategoryTv
            when (view.id) {
                R.id.dialog_category_btn1 -> categoryTv.text = "한식"
                R.id.dialog_category_btn2 -> categoryTv.text = "양식"
                R.id.dialog_category_btn3 -> categoryTv.text = "중식"
                R.id.dialog_category_btn4 -> categoryTv.text = "일식"
                R.id.dialog_category_btn5 -> categoryTv.text = "치킨"
                R.id.dialog_category_btn6 -> categoryTv.text = "디저트"
                R.id.dialog_category_btn7 -> categoryTv.text = "회"
                R.id.dialog_category_btn8 -> categoryTv.text = "아시안"
                R.id.dialog_category_btn9 -> categoryTv.text = "기타"
            }
            mAlertDialog.dismiss()
        }

        val btn1 = mDialogView.findViewById<AppCompatButton>(R.id.dialog_category_btn1)
        val btn2 = mDialogView.findViewById<AppCompatButton>(R.id.dialog_category_btn2)
        val btn3 = mDialogView.findViewById<AppCompatButton>(R.id.dialog_category_btn3)
        val btn4 = mDialogView.findViewById<AppCompatButton>(R.id.dialog_category_btn4)
        val btn5 = mDialogView.findViewById<AppCompatButton>(R.id.dialog_category_btn5)
        val btn6 = mDialogView.findViewById<AppCompatButton>(R.id.dialog_category_btn6)
        val btn7 = mDialogView.findViewById<AppCompatButton>(R.id.dialog_category_btn7)
        val btn8 = mDialogView.findViewById<AppCompatButton>(R.id.dialog_category_btn8)
        val btn9 = mDialogView.findViewById<AppCompatButton>(R.id.dialog_category_btn9)

        btn1.setOnClickListener(onClickListener)
        btn2.setOnClickListener(onClickListener)
        btn3.setOnClickListener(onClickListener)
        btn4.setOnClickListener(onClickListener)
        btn5.setOnClickListener(onClickListener)
        btn6.setOnClickListener(onClickListener)
        btn7.setOnClickListener(onClickListener)
        btn8.setOnClickListener(onClickListener)
        btn9.setOnClickListener(onClickListener)
    }

    private fun makePost(){
        val title : String = binding.makePostTitleEt.text.toString()
        val content : String = binding.makePostContentEt.text.toString()
        val delivery_cost : String = binding.makePostDeliveryCostEt.text.toString()

        val postService=PostService()
        postService.setPostView(this)
        postService.makePost(Post(title=title,content=content,delivery_cost=delivery_cost))
    }

    override fun onMakePostSuccess() {
        finish()
    }

    override fun onMakePostFailure(code:Int, msg:String) {
        when(code){
            2018,2019 -> Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
        }
    }
}