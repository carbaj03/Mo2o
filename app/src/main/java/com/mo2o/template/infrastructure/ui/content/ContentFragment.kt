package com.mo2o.template.infrastructure.ui.content


import android.support.v4.content.ContextCompat
import android.util.Log
import com.mo2o.template.GenericError
import com.mo2o.template.Id
import com.mo2o.template.R
import com.mo2o.template.future
import com.mo2o.template.infrastructure.api.TemplateService
import com.mo2o.template.infrastructure.api.model.File
import com.mo2o.template.infrastructure.extension.*
import com.mo2o.template.infrastructure.persistence.Cache
import com.mo2o.template.infrastructure.ui.common.BaseFragment
import com.mo2o.template.infrastructure.ui.common.ContentAdapter
import com.mo2o.template.infrastructure.ui.common.DividerDecoration
import dagger.android.support.AndroidSupportInjection
import kategory.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Response
import javax.inject.Inject

class ContentFragment : BaseFragment() {
    @Inject lateinit var template: TemplateService
    @Inject lateinit var preferences: Cache

    override fun getLayout() = R.layout.fragment_list

    override fun onCreate() {
        AndroidSupportInjection.inject(this)

        val args = Option.tupled(getArg<Id>(login), getArg<Id>(repository), getArg<Id>(path)).ev()
        getArg<Id>(repository).fold(
                { activity.tvTitle.text = "Sin Repo" },
                { activity.tvTitle.text = it.value }
        )
        future(
                service = { getContent(args) },
                error = { Either.Left(GenericError.ServerError) },
                complete = { complete(it) }
        )
    }

    fun getContent(args: Option<Tuple3<Id, Id, Id>>) = args.fold(
            { Either.Right(template.getContent("", "", "").execute()) },
            { Either.Right(template.getContent(it.a.value, it.b.value, it.c.value).execute()) }
    )

    fun complete(response: Either<GenericError.ServerError, Response<List<File>>>) = response.fold(
            { onError() },
            { onSuccess(it) }
    )

    private fun onError() = Log.e("Error", "not success")

    private fun onSuccess(response: Response<List<File>>) = response.isSuccessful(
            { Log.e("Error", "not success") },
            { show(response.body()!!) }
    )

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