package com.mo2o.template.infrastructure.extension

import android.app.Activity
import android.content.Intent
import com.mo2o.template.Command
import com.mo2o.template.Id
import com.mo2o.template.infrastructure.ui.common.setSlideRightAnimation

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

fun Activity.getExtra(): Id = intent?.getSerializableExtra(extra)?.let { it as Id } ?: Id("")
