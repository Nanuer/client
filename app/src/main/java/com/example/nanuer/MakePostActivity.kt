package com.example.nanuer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import android.widget.TextView
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

        binding.makePostFooterTimeTv.setOnClickListener {
            handleTimeDialog()
        }
    }

    private fun handleCategoryDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_category, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        val mAlertDialog = mBuilder.show()

        val onClickListener = View.OnClickListener { view ->
            val categoryTv = binding.makePostCategoryTv
            when (view.id) {
                R.id.dialog_category_btn1 -> categoryTv.text = "배달"
                R.id.dialog_category_btn2 -> categoryTv.text = "식재료"
                R.id.dialog_category_btn3 -> categoryTv.text = "택시"
                R.id.dialog_category_btn4 -> categoryTv.text = "구독"
            }
            mAlertDialog.dismiss()
        }

        val btn1 = mDialogView.findViewById<AppCompatButton>(R.id.dialog_category_btn1)
        val btn2 = mDialogView.findViewById<AppCompatButton>(R.id.dialog_category_btn2)
        val btn3 = mDialogView.findViewById<AppCompatButton>(R.id.dialog_category_btn3)
        val btn4 = mDialogView.findViewById<AppCompatButton>(R.id.dialog_category_btn4)

        btn1.setOnClickListener(onClickListener)
        btn2.setOnClickListener(onClickListener)
        btn3.setOnClickListener(onClickListener)
        btn4.setOnClickListener(onClickListener)
    }

    private fun handleTimeDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_time, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        val mAlertDialog = mBuilder.show()

        val ampm = mDialogView.findViewById<NumberPicker>(R.id.dialog_time_am_pm_np)
        val hour = mDialogView.findViewById<NumberPicker>(R.id.dialog_time_hour_np)
        val min = mDialogView.findViewById<NumberPicker>(R.id.dialog_time_min_np)
        val saveBtn = mDialogView.findViewById<TextView>(R.id.dialog_time_save_tv)

        ampm.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        hour.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        min.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        ampm.minValue = 0
        ampm.maxValue = 1
        ampm.displayedValues = arrayOf("오전", "오후")

        hour.minValue = 1
        hour.maxValue = 12

        min.minValue = 0
        min.maxValue = 11
        min.displayedValues = arrayOf("00","05","10","15","20","25","30","35","40","45","50","55")

        saveBtn.setOnClickListener {
            binding.makePostFooterTimeTv.text = "${ampm.displayedValues[ampm.value]} ${hour.value}시 ${min.displayedValues[min.value]}분"
            mAlertDialog.dismiss()
        }
    }

    private fun makePost(){
        val title : String = binding.makePostTitleEt.text.toString()
        val content : String = binding.makePostContentEt.text.toString()
        val delivery_cost : String = binding.makePostDeliveryCostEt.text.toString()
        val time : String = binding.makePostFooterTimeTv.toString()

        val postService=PostService()
        postService.setPostView(this)
        postService.makePost(Post(title=title,content=content,delivery_cost=delivery_cost,time=time))
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