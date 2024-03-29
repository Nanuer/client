package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.nanuer.databinding.FragmentPostBinding
import com.google.gson.Gson


class PostFragment : Fragment(), DeletePostView, GetUserIdView, GetProgressView{
    lateinit var binding: FragmentPostBinding
    private var gson:Gson = Gson()
    private var userId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater,container,false)

        getUserId()

        val postJson = arguments?.getString("post")
        val post = gson.fromJson(postJson, Post2::class.java)
        setInit(post)
        val university = arguments?.getString("university")

        if(post.progress=="Recruit"){
            binding.postRecruitTv.text = "모집중"
            binding.postFooterChattingBtn.visibility = View.VISIBLE
        }else{
            binding.postRecruitTv.text = "모집완료"
            binding.postFooterChattingBtn.visibility = View.GONE
        }

        binding.postHeaderBackIv.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main_fl, ListFragment().apply{
                    arguments = Bundle().apply {
                        putString("university", university)
                    }
                })
                commit()
            }
        }

        binding.postHeaderMenuIv.setOnClickListener{view ->
            Log.d("TEST","${userId},${post.userEntity?.userId}")
            if(userId==post.userEntity?.userId) handleUserPopUp(view,post)
            else handleNotUserPopUp(view)
        }

        binding.postFooterChattingBtn.setOnClickListener {
            val intent = Intent(requireActivity(),ChatActivity::class.java)
            intent.putExtra("postId",post.postId)
            intent.putExtra("title",post.title)
            intent.putExtra("userId",post.userEntity?.userId)
            intent.putExtra("categoryId",post.categoryEntity?.categoryId)
            if(post.cost_info!=0&&post.cost_info!=null){
                intent.putExtra("costInfo",post.cost_info)
            }
            if(post.delivery_cost!=0&&post.delivery_cost!=null){
                intent.putExtra("deliveryCost",post.delivery_cost)
            }
            startActivity(intent)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getProgress()
    }

    private fun handleUserPopUp(view: View, post:Post2){
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.post_popup_user, popup.menu)
        popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) { // 메뉴 아이템에 따라 동작 다르게 하기
                    R.id.change_completion -> Toast.makeText(requireContext(), "아직 지원하지 않는 기능입니다.", Toast.LENGTH_LONG).show()
                    R.id.modify_post -> Toast.makeText(requireContext(), "아직 지원하지 않는 기능입니다.", Toast.LENGTH_LONG).show()// updatePost(post.postId)
                    R.id.delete_post -> deletePost(post.postId)
                }
                return true
            }
        })
        popup.show()
    }

    private fun handleNotUserPopUp(view: View){
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.post_popup_not_user, popup.menu)
        popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) { // 메뉴 아이템에 따라 동작 다르게 하기
                    R.id.report_post -> Toast.makeText(requireContext(), "아직 지원하지 않는 기능입니다.", Toast.LENGTH_LONG).show()
                    R.id.share_post -> Toast.makeText(requireContext(), "아직 지원하지 않는 기능입니다.", Toast.LENGTH_LONG).show()
                }
                return true
            }
        })
        popup.show()
    }
    private fun getProgress(){
        val jwt = getJwt()
        val postJson = arguments?.getString("post")
        val post = gson.fromJson(postJson, Post2::class.java)
        val postId = post.postId
        val postService = PostService()
        postService.setGetProgressView(this)
        postService.getProgress(jwt!!,postId!!)
    }

    private fun getUserId(){
        val jwt = getJwt()
        val authService = AuthService()
        authService.setGetUserIdView(this)
        authService.getUserId(jwt!!)
    }

    private fun deletePost(post_id:Int?){
        val jwt = getJwt()
        val postService=PostService()
        postService.setDeletePostView(this)
        postService.deletePost(jwt!!, post_id!!)
    }

    private fun getJwt():String?{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("jwt","0")
    }

    private fun setInit(post:Post2){
        val category = getCategoryName(post.categoryEntity?.categoryId!!)
        binding.postCategoryTv.text = category
        binding.postTitleTv.text = post.title
        binding.postContentTv.text = post.content

        binding.postTimeTv.text = post.time
        if(post.time!="시간설정"&&post.time!=null){
            binding.postClockIconIv.visibility = View.VISIBLE
            binding.postTimeTv.visibility = View.VISIBLE
            binding.postTimeTv.text = post.time
        }
        if(post.location!=""&&post.location != null){
            binding.postLocationIconIv.visibility = View.VISIBLE
            binding.postLocationTv.text = post.location
        }
        if(post.cost_info != 0&&post.cost_info != null){
            binding.postCostInfoTv.text = "총 금액 ${post.cost_info}원"
        }

        binding.postUserNicknameTv.text = post.userEntity?.nickName
        if(post.delivery_cost!=0&&post.delivery_cost != null){
            binding.postFooterDeliveryFeeTv.text = "배달비 ${post.delivery_cost}원"
        }
        Glide.with(requireContext()).load(post.userEntity?.profileImg).error(R.drawable.profile).circleCrop().into(binding.postProfileIv)
        val createdDate = post.created_date
        val date = createdDate?.substring(0,10)
        val time = createdDate?.substring(11,16)
        binding.postCreateTimeTv.text = "${date} ${time}"
    }

    private fun getCategoryName(categoryId:Int):String{
        val category = when(categoryId){
            1->"배달"
            2->"식재료"
            3->"택시"
            4->"구독"
            5->"기타"
            else -> "기타"
        }
        return category
    }

    override fun onDeletePostSuccess() {
        val university = arguments?.getString("university")
        parentFragmentManager.beginTransaction().
            replace(R.id.main_fl, ListFragment().apply{
                arguments = Bundle().apply {
                    putString("university", university)
                }
            }).commit()
    }

    override fun onDeletePostFailure(code: Int, msg: String) {

    }

    override fun onGetUserIdSuccess(user_id: Int) {
        userId=user_id
    }

    override fun onGetUserIdFailure(code: Int, msg: String) {

    }

    override fun onGetProgressSuccess(progress: String) {
        if(progress=="Recruit"){
            binding.postRecruitTv.text = "모집중"
            binding.postFooterChattingBtn.visibility = View.VISIBLE
        }else{
            binding.postRecruitTv.text = "모집완료"
            binding.postFooterChattingBtn.visibility = View.GONE
        }
    }

    override fun onGetProgressFailure(code: Int, msg: String) {

    }
}