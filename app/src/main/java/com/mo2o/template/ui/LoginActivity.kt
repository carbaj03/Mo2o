package com.mo2o.template.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.mo2o.template.Cache
import com.mo2o.template.R
import com.mo2o.template.load
import com.mo2o.template.loadFragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject lateinit var preferences: Cache

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        setSupportActionBar(toolbar)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            preferences.put("name", tvName.text.toString())
            preferences.put("pass", tvPass.text.toString())
            load<MainActivity>()
        }
    }
}