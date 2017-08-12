package com.mo2o.template.infrastructure.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mo2o.template.GenericError
import com.mo2o.template.Id
import com.mo2o.template.R
import com.mo2o.template.future
import com.mo2o.template.infrastructure.api.TemplateService
import com.mo2o.template.infrastructure.api.model.User
import com.mo2o.template.infrastructure.extension.load
import com.mo2o.template.infrastructure.extension.login
import com.mo2o.template.infrastructure.extension.pass
import com.mo2o.template.infrastructure.extension.string
import com.mo2o.template.infrastructure.persistence.Cache
import com.mo2o.template.infrastructure.persistence.emptyValue
import dagger.android.AndroidInjection
import kategory.Either
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.toast
import retrofit2.Response
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject lateinit var preferences: Cache
    @Inject lateinit var template: TemplateService

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
            future(
                    service = { getOverview() },
                    error = { Either.Left(GenericError.ServerError) },
                    complete = { complete(it) }
            )
        }
    }

    private fun getOverview() = Either.Right(template.getUser().execute())

    fun complete(response: Either<GenericError.ServerError, Response<User>>) = response.fold(
            { onError() },
            { onSuccess(it) })

    private fun onError(): Unit = toast("mal")

    private fun onSuccess(response: Response<User>): Unit = when (response.isSuccessful) {
        true -> if (response.body() == null) {
            onError()
        } else {
            preferences.put(login, tvName.string)
            preferences.put(pass, tvPass.string)
            load<MainActivity>(listOf(login to Id(tvName.string)))
        }

        false -> onError()
    }
}