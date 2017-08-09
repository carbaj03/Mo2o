package com.mo2o.template.infrastructure.extension

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mo2o.template.R
import com.mo2o.template.infrastructure.ui.common.GlideLoader


const val HOME = android.R.id.home
const val OVERVIEW = R.id.bndOverview
const val REPOSITORIES = R.id.bndRepositories
const val STARS = R.id.bndStars
const val FOLLOWING = R.id.bndFollowing

const val PROFILE = R.id.profile

infix fun ViewGroup.inflate(res: Int) = LayoutInflater.from(context).inflate(res, this, false)

fun Action(f: () -> Unit): Boolean {
    f()
    return true
}

fun ImageView.load(url: String) { GlideLoader(Glide.with(context)).load(url, this) }
fun ImageView.loadCircle(url: String) { GlideLoader(Glide.with(context)).loadCircle(url, this) }

fun MenuInflater.make(menuRes: Int, menu: Menu) = Action { inflate(menuRes, menu) }