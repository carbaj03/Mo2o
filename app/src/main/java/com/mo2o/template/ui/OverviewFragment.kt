package com.mo2o.template.ui


import android.support.v4.content.ContextCompat
import android.util.Log
import com.mo2o.template.*
import com.mo2o.template.api.TemplateService
import com.mo2o.template.api.model.Repo
import dagger.android.support.AndroidSupportInjection
import kategory.Either
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Response
import javax.inject.Inject

class OverviewFragment: BaseFragment() {
    @Inject lateinit var template: TemplateService
    @Inject lateinit var preference: Cache

    lateinit var sessionAdapter: RepoAdapter

    override fun getLayout() = R.layout.fragment_list

    override fun onCreate() {
        setToolbar(R.string.main)
        AndroidSupportInjection.inject(this)

        Future {
            try {
                Either.Right(template.getRepos().execute())
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


    private fun onSuccess(response: Response<List<Repo>>) =
            response.isSuccessful
                    .apply { show(response.body()!!) }
                    .also { Log.e("Error", "not success") }

    fun show(repos: List<Repo>) = with(rvItems) {
        layoutManager = linearLayoutManager()
        val divider = DividerDecoration(ContextCompat.getColor(context, R.color.primary), 1f)
        addItemDecoration(divider)
        sessionAdapter = RepoAdapter(
                items = repos,
                listener = { toast(it.fullName) },
                holder = ::RepoViewHolder,
                layout = R.layout.item_repo)
        adapter = sessionAdapter
    }

}