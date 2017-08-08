package com.mo2o.template.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.mo2o.template.api.model.Follow
import com.mo2o.template.api.model.Repo
import com.mo2o.template.inflate

typealias RepoAdapter = AVH<RepoViewHolder, Repo>
typealias StarredAdapter = AVH<RepoViewHolder, Repo>
typealias FollowingAdapter = AVH<FollowViewHolder, Follow>

abstract class ViewHolder<in M>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(model: M)
}

open class AVH<VH : ViewHolder<M>, M>(
        val items: List<M>,
        val listener: (M) -> Unit,
        val holder: (view: View) -> VH,
        val layout: Int
) : RecyclerView.Adapter<VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = holder(parent.inflate(layout))

    override fun onBindViewHolder(holder: VH, position: Int) = with(holder) {
        bind(items[position])
        itemView.setOnClickListener { listener(items[position]) }
    }

    override fun getItemCount() = items.size
}