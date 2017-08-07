package com.mo2o.template.ui


import android.support.v4.app.Fragment
import android.util.Log
import com.mo2o.template.Cache
import com.mo2o.template.R
import com.mo2o.template.api.TemplateService
import com.mo2o.template.api.model.Repo
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
    @Inject lateinit var preference: Cache

    override fun getLayout() = R.layout.fragment_main

    override fun onCreate() {
        setToolbar(R.string.main)
        AndroidSupportInjection.inject(this)

        tvCache.text = "${preference.get("name", "name")} , ${preference.get("pass", "pass")}"

        val user = template.getRepos()
        user.enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                if (response.isSuccessful) {
                    tvApi.text = response.body()?.joinToString { it.fullName }
                } else {
                    Log.e("Error", "not success")
                }
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                // something went completely south (like no internet connection)
                Log.e("Error", t.message)
            }
        })
    }
}