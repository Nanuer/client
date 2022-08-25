package com.example.nanuer

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView
    private lateinit var findIdView: FindIdView
    private lateinit var getUserIdView: GetUserIdView
    private lateinit var getUserInfoView: GetUserInfoView
    private lateinit var updatePwView: UpdatePwView
    private lateinit var jwtIsValidateView: JwtIsValidateView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }
    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }
    fun setFindIdView(findIdView: FindIdView){
        this.findIdView = findIdView
    }
    fun setGetUserIdView(getUserIdView: GetUserIdView) {
        this.getUserIdView = getUserIdView
    }
    fun setGetUserInfoView(getUserInfoView: GetUserInfoView){
        this.getUserInfoView = getUserInfoView
    }
    fun setUpdatePview(updatePwView: UpdatePwView){
        this.updatePwView = updatePwView
    }
    fun setJwtIsValidateView(jwtIsValidateView: JwtIsValidateView){
        this.jwtIsValidateView = jwtIsValidateView
    }


    fun signUp(user : User){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(user).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("SIGNUP/SUCCESS", response.toString())
                val resp: AuthResponse = response.body()!!
                Log.d("SIGNUP/SUCCESS", resp.toString())
                when(resp.code){
                    1000-> signUpView.onSignUpSuccess()
                    else-> signUpView.onSignUpFailure()
                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }

        })
    }

    fun login(user : User){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(user).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("LOGIN/SUCCESS", response.toString())
                val resp: LoginResponse = response.body()!!
                Log.d("LOGIN/SUCCESS", resp.toString())
                when(val code = resp.code){
                    1000-> loginView.onLoginSuccess(code, resp.result)
                    else-> loginView.onLoginFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }

        })
    }

    fun findId(phone:String){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.findId(phone).enqueue(object: Callback<FindIdResponse>{
            override fun onResponse(call: Call<FindIdResponse>, response: Response<FindIdResponse>) {
                Log.d("FindId/SUCCESS", response.toString())
                val resp: FindIdResponse = response.body()!!
                Log.d("FindId/SUCCESS", resp.toString())
                when(val code = resp.code){
                    1000-> {
                        findIdView.onFindIdSuccess(resp.result)
                    }
                    else-> findIdView.onFindIdFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<FindIdResponse>, t: Throwable) {
                Log.d("FindId/FAILURE", t.message.toString())
            }
        })
    }

    fun getUserId(token:String) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.getUserId(token).enqueue(object : Callback<GetUserIdResponse> {
            override fun onResponse(
                call: Call<GetUserIdResponse>,
                response: Response<GetUserIdResponse>
            ) {
                Log.d("GETUSERID/SUCCESS", response.toString())
                val resp: GetUserIdResponse = response.body()!!
                Log.d("GETUSERID/SUCCESS", resp.toString())
                when (val code = resp.code) {
                    1000 -> getUserIdView.onGetUserIdSuccess(resp.result)
                    else -> getUserIdView.onGetUserIdFailure(code, resp.message)
                }
            }

            override fun onFailure(call: Call<GetUserIdResponse>, t: Throwable) {
                Log.d("GETUSERID/FAILURE", t.message.toString())
            }

        })
    }

    fun getUserInfo(token:String) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.getUserInfo(token).enqueue(object : Callback<GetUserInfoResponse> {
            override fun onResponse(
                call: Call<GetUserInfoResponse>,
                response: Response<GetUserInfoResponse>
            ) {
                Log.d("GETUSERINFO/SUCCESS", response.toString())
                val resp: GetUserInfoResponse = response.body()!!
                Log.d("GETUSERINFO/SUCCESS", resp.toString())
                when (val code = resp.code) {
                    1000 -> getUserInfoView.onGetUserInfoSuccess(resp.result)
                    else -> getUserInfoView.onGetUserInfoFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<GetUserInfoResponse>, t: Throwable) {
                Log.d("GETUSERINFO/FAILURE", t.message.toString())
            }
        })
    }

    fun upDatePw(phone:String, password:String) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.upDatePw(phone, password).enqueue(object : Callback<UpdatePwResponse> {
            override fun onResponse(
                call: Call<UpdatePwResponse>,
                response: Response<UpdatePwResponse>
            ) {
                Log.d("FindId/SUCCESS", response.toString())
                val resp: UpdatePwResponse = response.body()!!
                Log.d("FindId/SUCCESS", resp.toString())
                when (val code = resp.code) {
                    1000 -> {
                        updatePwView.onUpdatePwSuccess(resp.result)

                    }
                    else -> updatePwView.onUpdatePwFailure(code, resp.message)
                }
            }

            override fun onFailure(call: Call<UpdatePwResponse>, t: Throwable) {
                Log.d("FindId/FAILURE", t.message.toString())
            }

        })

    }

    fun jwtIsValidate(token:String) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.jwtIsValidate(token).enqueue(object : Callback<JwtIsValidateResponse> {
            override fun onResponse(
                call: Call<JwtIsValidateResponse>,
                response: Response<JwtIsValidateResponse>
            ) {
                Log.d("JwtIsValidate/SUCCESS", response.toString())
                val resp: JwtIsValidateResponse = response.body()!!
                Log.d("JwtIsValidate/SUCCESS", resp.toString())
                when (val code = resp.code) {
                    1000 -> jwtIsValidateView.onJwtIsValidateSuccess(resp.result)
                    else -> jwtIsValidateView.onJwtIsValidateFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<JwtIsValidateResponse>, t: Throwable) {
                Log.d("JwtIsValidate/FAILURE", t.message.toString())
            }
        })
    }



}