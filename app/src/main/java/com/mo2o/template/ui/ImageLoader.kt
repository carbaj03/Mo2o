package com.mo2o.template.ui

import android.widget.ImageView

interface ImageLoader {
    fun load(url: String, view: ImageView)
    fun loadCircle(url: String, view: ImageView)
}
