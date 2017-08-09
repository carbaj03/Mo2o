package com.mo2o.template.infrastructure.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mo2o.template.infrastructure.persistence.Cache
import com.mo2o.template.R
import com.mo2o.template.infrastructure.extension.load
import dagger.android.AndroidInjection
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
        tvName.setText(preferences.get("login", ""))
        tvPass.setText(preferences.get("pass", ""))

        btnLogin.setOnClickListener {
            preferences.put("login", tvName.text.toString())
            preferences.put("pass", tvPass.text.toString())
            load<MainActivity>()
        }
    }
}