package com.example.nanuer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentPostBinding
import com.google.gson.Gson


class PostFragment : Fragment(), DeletePostView, GetUserIdView{
    lateinit var binding: FragmentPostBinding
    private var gson:Gson = Gson()
    private var userId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater,container,false)

        setUniv()

//        getUserId()

        val postJson = arguments?.getString("post")
        val post = gson.fromJson(postJson, Post2::class.java)
        setInit(post)

        binding.postHeaderBackIv.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main_fl, ListFragment())
                commit()
            }
        }

        binding.postHeaderMenuIv.setOnClickListener{view ->
            if(userId==post.userEntity?.userId) handleUserPopUp(view,post)
            else handleNotUserPopUp(view)
        }

        binding.postFooterChattingBtn.setOnClickListener {
            val intent = Intent(requireActivity(),ChatActivity::class.java)
            intent.putExtra("postId",post.postId)
            intent.putExtra("title",post.title)
            startActivity(intent)
        }

        return binding.root
    }
    private fun setUniv(){
        val university = arguments?.getString("university")
        binding.postUserNicknameTv.text = university
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
        return spf!!.getString("Jwt","0")
    }

    private fun setInit(post:Post2){
        val category = getCategoryName(post.categoryEntity?.categoryId!!)
        binding.postCategoryTv.text = category
        binding.postTitleTv.text = post.title
        binding.postContentTv.text = post.content
        binding.postTimeTv.text = post.time
        binding.postLocationTv.text = post.location
        binding.postCreateTimeTv.text = post.created_date
        binding.postUserNicknameTv.text = post.userEntity?.nickName
//        binding.postProfileIv.setImageResource(post.userEntity.profileImg)
        binding.postFooterDeliveryFeeTv.text = post.delivery_cost
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
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.main_fl, ListFragment())
            commit()
        }
    }

    override fun onDeletePostFailure(code: Int, msg: String) {

    }

    override fun onGetUserIdSuccess(user_id: Int) {
        userId=user_id
    }

    override fun onGetUserIdFailure(code: Int, msg: String) {

    }
}