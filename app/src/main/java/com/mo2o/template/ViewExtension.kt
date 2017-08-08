package com.mo2o.template

import android.view.LayoutInflater
import android.view.ViewGroup


const val HOME = android.R.id.home
const val OVERVIEW = R.id.bndOverview
const val REPOSITORIES = R.id.bndRepositories
const val STARS = R.id.bndStars
const val FOLLOWING = R.id.bndFollowing

infix fun ViewGroup.inflate(res: Int) = LayoutInflater.from(context).inflate(res, this, false)

fun Action(f: () -> Unit): Boolean {
    f()
    return true
}
