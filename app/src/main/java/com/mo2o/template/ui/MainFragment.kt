package com.mo2o.template.ui


import android.support.v4.content.ContextCompat
import android.util.Log
import com.mo2o.template.Cache
import com.mo2o.template.R
import com.mo2o.template.api.TemplateService
import com.mo2o.template.api.model.Repo
import com.mo2o.template.linearLayoutManager
import com.mo2o.template.setToolbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainFragment : BaseFragment() {
    @Inject lateinit var template: TemplateService
    @Inject lateinit var preference: Cache

    lateinit var sessionAdapter: RepoAdapter

    override fun getLayout() = R.layout.fragment_list

    override fun onCreate() {
        setToolbar(R.string.main)
        AndroidSupportInjection.inject(this)

        val user = template.getRepos()
        user.enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                response.isSuccessful.apply { show(response.body()!!) }.also { Log.e("Error", "not success") }
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                Log.e("Error", t.message)
            }
        })
    }

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