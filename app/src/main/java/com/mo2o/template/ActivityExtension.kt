package com.mo2o.template

import android.app.Activity
import android.content.Intent

/**
 * Created by alejandro on 7/08/17.
 */
inline fun <reified T : Activity> Activity.load(pairs: List<Pair<String, Command>> = listOf()) {
    goToActivity<T>(pairs)
    finish()
    setSlideRightAnimation()
}

inline fun <reified T : Activity> Activity.goToActivity(pairs: List<Pair<String, Command>>) {
    val intent = Intent(this, T::class.java)
    pairs.map { intent.putExtra(it.first, it.second) }
    startActivity(intent)
}

