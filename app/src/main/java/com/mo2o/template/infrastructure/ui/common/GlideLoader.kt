package com.mo2o.template.infrastructure.ui.common

import android.widget.ImageView
import com.bumptech.glide.RequestManager


class GlideLoader(val glide: RequestManager) : ImageLoader {
    override fun load(url: String, view: ImageView) {
        glide.load(url).into(view)
    }

    override fun loadCircle(url: String, view: ImageView) {
        glide.load(url).transform(CircleTransform(view.context)).into(view)
    }
}
