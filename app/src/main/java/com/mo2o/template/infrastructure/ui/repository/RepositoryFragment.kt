package com.mo2o.template.infrastructure.ui.repository


import android.util.Log
import com.mo2o.template.Future
import com.mo2o.template.GenericError
import com.mo2o.template.Id
import com.mo2o.template.R
import com.mo2o.template.infrastructure.api.TemplateService
import com.mo2o.template.infrastructure.api.model.Repo
import com.mo2o.template.infrastructure.domain.Service
import com.mo2o.template.infrastructure.extension.getArgId
import com.mo2o.template.infrastructure.invoke.Logic
import com.mo2o.template.infrastructure.invoke.Request
import com.mo2o.template.infrastructure.ui.common.BaseFragment
import dagger.android.support.AndroidSupportInjection
import kategory.Either
import kategory.Option
import retrofit2.Response
import javax.inject.Inject

class RepositoryFragment : BaseFragment() {
    @Inject lateinit var template: TemplateService

    override fun getLayout() = R.layout.fragment_list

    override fun onCreate() {
        AndroidSupportInjection.inject(this)

        loadData(repo(getArgId()), success())

        logic(execute(getArgId()))
    }

    fun execute(id: Option<Id>) = when (id) {
        is Option.None -> Either.Left(GenericError.NetworkError)
        is Option.Some -> Either.Right(template.getRepos(id.value.value).execute())
    }

    fun <I : Request, E : GenericError, R> logic(
            service: Service<I, R, E>,
            params: Option<I> = Option.None,
            success: (R) -> Unit,
            error: (E) -> Unit) = with(Future { with(service) { service.execute(params) } })
    {
        onComplete {
            when (it) {
                is Either.Left -> error(it.a)
                is Either.Right -> success(it.b)
            }
        }
    }



    fun loadData(repo: () -> Response<List<Repo>>, f: (Either<GenericError, Response<List<Repo>>>) -> Unit) =
            Future {
                try {
                    Either.Right(repo())
                } catch (e: Exception) {
                    Either.Left(GenericError.ServerError)
                }
            }.onComplete {
                f(it)
            }

    fun repo(id: Option<Id>, templa: TemplateService): () -> Response<List<Repo>> = when (id) {
        is Option.None -> tem(templa)
        is Option.Some -> template.getRepos(id.value.value).execute()
    }

    fun tem(templa: TemplateService): () -> Response<List<Repo>> = templa.getRepos().execute()

    fun success(e: Either<GenericError, Response<List<Repo>>>): Unit = when (e) {
        is Either.Right -> onSuccess(e.b)
        is Either.Left -> error()
    }

    private fun onSuccess(response: Response<List<Repo>>): Unit =
            response.isSuccessful
                    .let { show(response.body()!!) }
                    .also { error() }

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

    fun error(): Unit {
        Log.e("Error", "not success")
    }

}