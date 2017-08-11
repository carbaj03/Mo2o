package com.mo2o.template.infrastructure.ui.content


import android.support.v4.content.ContextCompat
import android.util.Log
import com.mo2o.template.GenericError
import com.mo2o.template.Id
import com.mo2o.template.R
import com.mo2o.template.future
import com.mo2o.template.infrastructure.api.TemplateService
import com.mo2o.template.infrastructure.api.model.File
import com.mo2o.template.infrastructure.extension.getArg
import com.mo2o.template.infrastructure.extension.linearLayoutManager
import com.mo2o.template.infrastructure.extension.login
import com.mo2o.template.infrastructure.ui.common.BaseFragment
import com.mo2o.template.infrastructure.ui.common.ContentAdapter
import com.mo2o.template.infrastructure.ui.common.DividerDecoration
import dagger.android.support.AndroidSupportInjection
import kategory.Either
import kategory.Option
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Response
import javax.inject.Inject

class ContentFragment : BaseFragment() {
    @Inject lateinit var template: TemplateService

    override fun getLayout() = R.layout.fragment_list

    override fun onCreate() {
        AndroidSupportInjection.inject(this)

        future(
                service = { getRepositories(getArg(login)) },
                error = { Either.Left(GenericError.ServerError) },
                complete = { complete(it) }
        )
    }

    fun getRepositories(id: Option<Id>) = when (id) {
        is Option.None -> Either.Right(template.getContent("carbaj03", "Template", "app").execute())
        is Option.Some -> Either.Right(template.getContent("carbaj03", "Template", "").execute())
    }

    fun complete(response: Either<GenericError.ServerError, Response<List<File>>>) = when (response) {
        is Either.Right -> onSuccess(response.b)
        is Either.Left -> onError()
    }

    private fun onError() = Log.e("Error", "not success")

    private fun onSuccess(response: Response<List<File>>): Boolean =
            response.isSuccessful
                    .apply { show(response.body()!!) }
                    .also { Log.e("Error", "not success") }

    fun show(repos: List<File>) = with(rvItems) {
        layoutManager = linearLayoutManager()
        val divider = DividerDecoration(ContextCompat.getColor(context, R.color.primary), 1f)
        addItemDecoration(divider)
        adapter = ContentAdapter(
                items = repos,
                listener = { toast(it.path) },
                holder = ::ContentViewHolder,
                layout = R.layout.item_content)
    }


}