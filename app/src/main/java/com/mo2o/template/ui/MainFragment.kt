package com.mo2o.template.ui


import android.support.v4.app.Fragment
import android.util.Log
import com.mo2o.template.R
import com.mo2o.template.api.TemplateService
import com.mo2o.template.api.model.User
import com.mo2o.template.setToolbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class MainFragment : BaseFragment() {

    @Inject lateinit var template: TemplateService

    override fun getLayout() = R.layout.fragment_main

    override fun onCreate() {
        setToolbar(R.string.main)
        AndroidSupportInjection.inject(this)
        val user = template.getUser()
        user.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    tvText.text = response.body()?.name
                } else {
                    // error response, no access to resource?
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.message)
            }
        })
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)

    }
}