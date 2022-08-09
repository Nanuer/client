package com.example.nanuer

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nanuer.databinding.ActivitySetUnivBinding


class SetUnivActivity :AppCompatActivity() {
    lateinit var binding: ActivitySetUnivBinding

    var univs = arrayOf("숭실대", "인하대", "한양대 에리카")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUnivBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setUnivOkTv.setOnClickListener {
            // updateUniv - 현재유저정보에서 대학교 상태 변경하기(+ 회원가입하고 나서 이 창을 바로 띄어준다음 대학교 상태를 업데이트 하도록 함, 따로 대학교 수정 창 필요할지도..?
        }

        val spinner = binding.setUnivSpinner
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, univs
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(applicationContext, univs.get(position), Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
//                Toast.makeText(applicationContext, "선택되지 않음", Toast.LENGTH_LONG).show()
            }
        })
    }
}