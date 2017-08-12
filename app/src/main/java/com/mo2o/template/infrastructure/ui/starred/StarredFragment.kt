package com.mo2o.template.infrastructure.ui.starred


import android.support.v4.content.ContextCompat
import android.util.Log
import com.mo2o.template.GenericError
import com.mo2o.template.Id
import com.mo2o.template.R
import com.mo2o.template.future
import com.mo2o.template.infrastructure.api.TemplateService
import com.mo2o.template.infrastructure.api.model.Repo
import com.mo2o.template.infrastructure.extension.getArg
import com.mo2o.template.infrastructure.extension.invoke
import com.mo2o.template.infrastructure.extension.linearLayoutManager
import com.mo2o.template.infrastructure.extension.login
import com.mo2o.template.infrastructure.ui.common.BaseFragment
import com.mo2o.template.infrastructure.ui.common.DividerDecoration
import com.mo2o.template.infrastructure.ui.common.RepoAdapter
import com.mo2o.template.infrastructure.ui.repository.RepositoryViewHolder
import dagger.android.support.AndroidSupportInjection
import kategory.Either
import kategory.Option
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Response
import javax.inject.Inject

class StarredFragment : BaseFragment() {
    @Inject lateinit var template: TemplateService

    override fun getLayout() = R.layout.fragment_list

    override fun onCreate() {
        AndroidSupportInjection.inject(this)

        future(
                service = { getStarred(getArg(login)) },
                error = { Either.Left(GenericError.ServerError) },
                complete = { complete(it) }
        )
    }

    private fun getStarred(login: Option<Id>) = login.fold(
            { Either.Right(template.getStarred().execute()) },
            { Either.Right(template.getStarred(it.value).execute()) }
    )

    fun complete(response: Either<GenericError.ServerError, Response<List<Repo>>>) = response.fold(
            { onError() },
            { onSuccess(it) }
    )

    private fun onError() = Log.e("Error", "not success")

    private fun onSuccess(response: Response<List<Repo>>) = response.isSuccessful(
            { Log.e("Error", "not success") },
            { show(response.body()!!) }
    )

    fun show(repos: List<Repo>) = with(rvItems) {
        layoutManager = linearLayoutManager()
        val divider = DividerDecoration(ContextCompat.getColor(context, R.color.primary), 1f)
        addItemDecoration(divider)
        adapter = RepoAdapter(
                items = repos,
                listener = { toast(it.fullName) },
                holder = ::RepositoryViewHolder,
                layout = R.layout.item_repo)
    }

}