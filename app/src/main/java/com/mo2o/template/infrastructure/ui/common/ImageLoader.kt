package com.mo2o.template.infrastructure.ui.common

import android.widget.ImageView

interface ImageLoader {
    fun load(url: String, view: ImageView)
    fun loadCircle(url: String, view: ImageView)
}
