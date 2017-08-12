package com.mo2o.template.infrastructure.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mo2o.template.Id
import com.mo2o.template.R
import com.mo2o.template.infrastructure.extension.*
import com.mo2o.template.infrastructure.persistence.Cache
import com.mo2o.template.infrastructure.persistence.emptyValue
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject lateinit var preferences: Cache

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        AndroidInjection.inject(this)

        setSupportActionBar(toolbar)

        

        tvName.setText(preferences.get(login, emptyValue))
        tvPass.setText(preferences.get(pass, emptyValue))

        btnLogin.setOnClickListener {
            preferences.put(login, tvName.string)
            preferences.put(pass, tvPass.string)
            load<MainActivity>(listOf(login to Id(tvName.string)))
        }
    }
}