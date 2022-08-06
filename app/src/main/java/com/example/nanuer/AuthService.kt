package com.example.nanuer

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView
    private lateinit var findIdView: FindIdView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }
    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }
    fun setFindIdView(findIdView: FindIdView){
        this.findIdView = findIdView
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

    fun findId(user: User){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.find(user).enqueue(object: Callback<FindIdResponse>{
            override fun onResponse(call: Call<FindIdResponse>, response: Response<FindIdResponse>) {
                Log.d("LOGIN/SUCCESS", response.toString())
                val resp: FindIdResponse = response.body()!!
                Log.d("LOGIN/SUCCESS", resp.toString())
                when(val code = resp.code){
                    1000-> findIdView.onFindIdSuccess(code, resp.result)
                    else-> findIdView.onFindIdFailure(code, resp.message)
                }
            }
            override fun onFailure(call: Call<FindIdResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }

        })

    }
}