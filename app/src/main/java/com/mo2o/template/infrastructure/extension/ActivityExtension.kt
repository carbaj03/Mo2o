package com.mo2o.template.infrastructure.extension

import android.app.Activity
import android.content.Intent
import com.mo2o.template.Command
import com.mo2o.template.Id
import com.mo2o.template.infrastructure.ui.common.setFadeInOutAnimation
import java.util.stream.Stream

inline fun <reified T : Activity> Activity.load(pairs: List<Pair<String, Command>> = listOf()) {
    goToActivity<T>(pairs)
    finish()
    setFadeInOutAnimation()
}

inline fun <reified T : Activity> Activity.goToActivity(pairs: List<Pair<String, Command>>) {
    val intent = Intent(this, T::class.java)
    pairs.map { intent.putExtra(it.first, it.second) }
    startActivity(intent)
}

fun Activity.getExtra(extra: String): Id = intent?.getSerializableExtra(extra)?.let { it as Id } ?: Id("")
fun Activity.getExtraPair(extra: String): Pair<String, Command> = extra to getExtra(extra)