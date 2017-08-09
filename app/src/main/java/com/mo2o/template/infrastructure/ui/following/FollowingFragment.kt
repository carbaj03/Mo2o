package com.mo2o.template.infrastructure.ui.following


import android.support.v4.content.ContextCompat
import android.util.Log
import com.mo2o.template.*
import com.mo2o.template.infrastructure.api.TemplateService
import com.mo2o.template.infrastructure.api.model.Follow
import com.mo2o.template.infrastructure.extension.linearLayoutManager
import com.mo2o.template.infrastructure.extension.setToolbar
import com.mo2o.template.infrastructure.ui.common.BaseFragment
import com.mo2o.template.infrastructure.ui.DividerDecoration
import com.mo2o.template.infrastructure.ui.FollowingAdapter
import dagger.android.support.AndroidSupportInjection
import kategory.Either
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Response
import javax.inject.Inject

class FollowingFragment : BaseFragment() {
    @Inject lateinit var template: TemplateService

    override fun getLayout() = R.layout.fragment_list

    override fun onCreate() {
        setToolbar(R.string.main)
        AndroidSupportInjection.inject(this)
        Future {
            try {
                Either.Right(template.getFollowing().execute())
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

    private fun onSuccess(response: Response<List<Follow>>) =
            response.isSuccessful
                    .apply { show(response.body()!!) }
                    .also { Log.e("Error", "not success") }

    fun show(following: List<Follow>) = with(rvItems) {
        layoutManager = linearLayoutManager()
        val divider = DividerDecoration(ContextCompat.getColor(context, R.color.primary), 1f)
        addItemDecoration(divider)
        adapter = FollowingAdapter(
                items = following,
                listener = { toast(it.login) },
                holder = ::FollowViewHolder,
                layout = R.layout.item_follow)
    }

}