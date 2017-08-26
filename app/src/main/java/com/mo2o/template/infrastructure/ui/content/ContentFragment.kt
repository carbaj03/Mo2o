package com.mo2o.template.infrastructure.ui.content


import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
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

object ArgNotFound
data class Result(val login: Id, val repository: Id, val path: Id)

class ContentFragment : BaseFragment() {
    @Inject lateinit var template: TemplateService
    @Inject lateinit var preferences: Cache

    override fun getLayout() = R.layout.fragment_list

    override fun onCreate() {
        AndroidSupportInjection.inject(this)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

//        val args = Option.tupled(getArg<Id>(login), getArg<Id>(repository), getArg<Id>(path)).ev()
        getArg<Id>(repository).fold(
                { activity.tvTitle.text = "Sin Repo" },
                { activity.tvTitle.text = it.value }
        )

        val resultsB: Either<ArgNotFound, Result> = Option.binding {
            val l = getArg<Id>(login).bind()
            val r = getArg<Id>(repository).bind()
            val p = getArg<Id>(path).bind()
            yields(Result(l, r, p))
        }.ev().fold(
                { Either.Left(ArgNotFound) },
                { Either.Right(it) }
        )

        future(
                service = { getContent(resultsB) },
                error = { Either.Left(GenericError.ServerError) },
                complete = { complete(it) }
        )
    }

    fun getContent(args: Either<ArgNotFound, Result>) = args.fold(
            { Either.Right(template.getContent("", "", "").execute()) },
            { Either.Right(template.getContent(it.login.value, it.path.value, it.repository.value).execute()) }
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
        val sortedWith = repos.sortedWith(compareBy({ it.type }))

        adapter = ContentAdapter(
                items = sortedWith,
                listener = { toast(it.path) },
                holder = ::ContentViewHolder,
                layout = R.layout.item_content)
    }


}

//val resultsB: Either<ArgNotFound, Result> = Option.binding {
//    val l = getArg<Id>(login).bind()
//    val r = getArg<Id>(repository).bind()
//    val p = getArg<Id>(path).bind()
//    yields(Result(l, r, p))
//}.ev().fold(
//        { ArgNotFound.left() },
//        { it.right() }
//)