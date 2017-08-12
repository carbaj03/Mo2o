package com.mo2o.template.infrastructure.ui.following


import android.support.v4.content.ContextCompat
import android.util.Log
import com.mo2o.template.Future
import com.mo2o.template.GenericError
import com.mo2o.template.Id
import com.mo2o.template.R
import com.mo2o.template.infrastructure.api.TemplateService
import com.mo2o.template.infrastructure.api.model.Follow
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
        Future {
            try {
                val argId = getArg<Id>(login)
                when (argId) {
                    is Option.None -> Either.Right(template.getFollowing().execute())
                    is Option.Some -> Either.Right(template.getFollowing(argId.value.value).execute())
                }
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

    private fun onSuccess(response: Response<List<Follow>>) = response.isSuccessful(
            { Log.e("Error", "not success") },
            { show(response.body()!!) }
    )

    fun show(following: List<Follow>) = with(rvItems) {
        layoutManager = linearLayoutManager()
        val divider = DividerDecoration(ContextCompat.getColor(context, R.color.primary), 1f)
        addItemDecoration(divider)
        adapter = FollowingAdapter(
                items = following,
                listener = { load<MainActivity>(listOf(login to Id(it.login))) },
                holder = ::FollowViewHolder,
                layout = R.layout.item_follow)
    }

}