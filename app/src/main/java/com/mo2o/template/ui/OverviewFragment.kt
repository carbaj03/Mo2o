package com.mo2o.template.ui


import android.util.Log
import com.bumptech.glide.Glide
import com.mo2o.template.Future
import com.mo2o.template.GenericError
import com.mo2o.template.R
import com.mo2o.template.api.TemplateService
import com.mo2o.template.api.model.User
import com.mo2o.template.setToolbar
import dagger.android.support.AndroidSupportInjection
import kategory.Either
import kotlinx.android.synthetic.main.fragment_overview.*
import retrofit2.Response
import javax.inject.Inject

class OverviewFragment : BaseFragment() {
    @Inject lateinit var template: TemplateService

    override fun getLayout() = R.layout.fragment_overview

    override fun onCreate() {
        setToolbar(R.string.main)
        AndroidSupportInjection.inject(this)

        Future {
            try {
                Either.Right(template.getUser().execute())
            } catch (e: Exception) {
                Either.Left(GenericError.ServerError)
            }
        }.onComplete {
            when (it) {
                is Either.Right -> onSuccess(it.b)
                is Either.Left -> onError()
            }
        }
    }

    private fun onError() = Log.e("Error", "not success")


    private fun onSuccess(response: Response<User>) =
            response.isSuccessful
                    .apply { show(response.body()!!) }
                    .also { Log.e("Error", "not success") }

    fun show(user: User) = with(user) {
        Glide.with(context).load(user.avatarUrl).into(ivAvatar)
        tvEmail.text = email
        tvFullName.text = name
        tvAlias.text = login
    }

}