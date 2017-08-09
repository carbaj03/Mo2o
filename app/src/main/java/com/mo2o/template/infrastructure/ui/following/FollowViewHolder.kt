package com.mo2o.template.infrastructure.ui.following

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mo2o.template.R
import com.mo2o.template.infrastructure.api.model.Follow
import com.mo2o.template.infrastructure.extension.load
import com.mo2o.template.infrastructure.ui.common.ViewHolder
import org.jetbrains.anko.find

class FollowViewHolder(view: View) : ViewHolder<Follow>(view) {
    var tvLogin = view.find<TextView>(R.id.tvLogin)
    var avatar = view.find<ImageView>(R.id.ivFollowAvatar)

    override fun bind(follow: Follow) = with(follow) {
        tvLogin.text = login
        avatar.load(follow.avatarUrl)
    }
}
