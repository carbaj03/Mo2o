package com.mo2o.template.infrastructure.ui.repository


import android.support.v4.content.ContextCompat
import android.util.Log
import com.mo2o.template.*
import com.mo2o.template.infrastructure.api.TemplateService
import com.mo2o.template.infrastructure.api.model.Repo
import com.mo2o.template.infrastructure.extension.*
import com.mo2o.template.infrastructure.ui.common.BaseFragment
import com.mo2o.template.infrastructure.ui.common.DividerDecorationK
import com.mo2o.template.infrastructure.ui.common.RepoAdapter
import com.mo2o.template.infrastructure.ui.content.ContentFragment
import dagger.android.support.AndroidSupportInjection
import kategory.Either
import kotlinx.android.synthetic.main.fragment_list.*
import retrofit2.Response
import javax.inject.Inject

class RepositoryFragment : BaseFragment() {
    @Inject lateinit var template: TemplateService

    override fun getLayout() = R.layout.fragment_list

    override fun onCreate() {
        AndroidSupportInjection.inject(this)

        future(
                service = { getRepositories(getArgE(login)) },
                error = { Either.Left(GenericError.ServerError) },
                complete = { complete(it) }
        )
    }

    fun getRepositories(id: Id) = Either.Right(template.getRepos(id.value).execute())

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
        val divider = DividerDecorationK(ContextCompat.getColor(context, R.color.primary), 1f)
        addItemDecoration(divider)
        adapter = RepoAdapter(
                items = repos,
                listener = {
                    loadFragment<ContentFragment>(listOf(
                            login to Id(getArgE<Id>(login).value),
                            repository to Id(it.name),
                            path to Id("")
                    ))
                },
                holder = ::RepositoryViewHolder,
                layout = R.layout.item_repo)
    }
}