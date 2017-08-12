package com.mo2o.template.infrastructure.ui.overview


import android.util.Log
import com.mo2o.template.GenericError
import com.mo2o.template.Id
import com.mo2o.template.R
import com.mo2o.template.future
import com.mo2o.template.infrastructure.api.TemplateService
import com.mo2o.template.infrastructure.api.model.User
import com.mo2o.template.infrastructure.extension.*
import com.mo2o.template.infrastructure.ui.common.BaseFragment
import dagger.android.support.AndroidSupportInjection
import kategory.Either
import kategory.Option
import kotlinx.android.synthetic.main.fragment_overview.*
import retrofit2.Response
import javax.inject.Inject

class OverviewFragment : BaseFragment() {
    @Inject lateinit var template: TemplateService

    override fun getLayout() = R.layout.fragment_overview

    override fun onCreate() {
        AndroidSupportInjection.inject(this)

        future(
                service = { getOverview(getArg(login)) },
                error = { Either.Left(GenericError.ServerError) },
                complete = { complete(it) }
        )
    }

    private fun getOverview(login: Option<Id>) = login.fold(
            { Either.Right(template.getUser().execute()) },
            { Either.Right(template.getUser(it.value).execute()) }
    )

    fun complete(response: Either<GenericError.ServerError, Response<User>>) = response.fold(
            { onError() },
            { onSuccess(it) }
    )

    private fun onError() = Log.e("Error", "not success")

    private fun onSuccess(response: Response<User>) = response.isSuccessful(
            { Log.e("Error", "not success") },
            { show(response.body()) }
    )

    fun show(user: User?) = withNotNull(user) {
        ivAvatar.loadCircle(avatarUrl)
        tvEmail.text = email
        tvFullName.text = name
        tvAlias.text = login
    }
}