package com.mo2o.template.infrastructure.ui.following


import android.support.v4.content.ContextCompat
import android.util.Log
import com.mo2o.template.*
import com.mo2o.template.infrastructure.api.TemplateService
import com.mo2o.template.infrastructure.api.model.Follow
import com.mo2o.template.infrastructure.api.model.Repo
import com.mo2o.template.infrastructure.extension.*
import com.mo2o.template.infrastructure.ui.MainActivity
import com.mo2o.template.infrastructure.ui.common.BaseFragment
import com.mo2o.template.infrastructure.ui.common.DividerDecoration
import com.mo2o.template.infrastructure.ui.common.FollowingAdapter
import dagger.android.support.AndroidSupportInjection
import kategory.Either
import kategory.Option
import kotlinx.android.synthetic.main.fragment_list.*
import retrofit2.Response
import javax.inject.Inject

class FollowingFragment : BaseFragment() {
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
            { Either.Right(template.getFollowing().execute()) },
            { Either.Right(template.getFollowing(it.value).execute()) }
    )

    fun complete(response: Either<GenericError.ServerError, Response<List<Follow>>>) = response.fold(
            { onError() },
            { onSuccess(it) }
    )

    private fun onError() = Log.e("Error", "not success")

    private fun onSuccess(response: Response<List<Follow>>) = response.isSuccessful(
            { Log.e("Error", "not success") },
            { show(response.body()!!) }
    )

    fun show(following: List<Follow>) = with(rvItems) {
        layoutManager = linearLayoutManager()
        val divider = DividerDecoration(ContextCompat.getColor(context, R.color.primary), 1f)
        addItemDecoration(divider)
        val sortedWith = following.sortedWith(compareBy({ it.login }))
        adapter = FollowingAdapter(
                items = sortedWith,
                listener = { load<MainActivity>(listOf(login to Id(it.login))) },
                holder = ::FollowViewHolder,
                layout = R.layout.item_follow)
    }

}