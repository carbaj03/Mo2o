package com.mo2o.template.infrastructure.ui.repository


import android.support.v4.content.ContextCompat
import android.util.Log
import com.mo2o.template.GenericError
import com.mo2o.template.Id
import com.mo2o.template.R
import com.mo2o.template.future
import com.mo2o.template.infrastructure.api.TemplateService
import com.mo2o.template.infrastructure.api.model.Repo
import com.mo2o.template.infrastructure.extension.getArgId
import com.mo2o.template.infrastructure.extension.linearLayoutManager
import com.mo2o.template.infrastructure.ui.common.BaseFragment
import com.mo2o.template.infrastructure.ui.common.DividerDecoration
import com.mo2o.template.infrastructure.ui.common.RepoAdapter
import dagger.android.support.AndroidSupportInjection
import kategory.Either
import kategory.Option
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Response
import javax.inject.Inject

class RepositoryFragment : BaseFragment() {
    @Inject lateinit var template: TemplateService

    override fun getLayout() = R.layout.fragment_list

    override fun onCreate() {
        AndroidSupportInjection.inject(this)

        future(
                service = { getRepositories(getArgId()) },
                error = { Either.Left(GenericError.ServerError) },
                complete = { complete(it) }
        )
    }

    fun getRepositories(id: Option<Id>): Either.Right<Response<List<Repo>>> = when (id) {
        is Option.None -> Either.Right(template.getRepos().execute())
        is Option.Some -> Either.Right(template.getRepos(id.value.value).execute())
    }

    fun complete(response: Either<GenericError.ServerError, Response<List<Repo>>>) = when (response) {
        is Either.Right -> onSuccess(response.b)
        is Either.Left -> onError()
    }

    private fun onError() = Log.e("Error", "not success")

    private fun onSuccess(response: Response<List<Repo>>): Boolean =
            response.isSuccessful
                    .apply { show(response.body()!!) }
                    .also { Log.e("Error", "not success") }

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