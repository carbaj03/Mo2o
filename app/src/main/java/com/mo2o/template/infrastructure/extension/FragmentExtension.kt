package com.mo2o.template.infrastructure.extension

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.mo2o.template.Command
import com.mo2o.template.R
import com.mo2o.template.infrastructure.ui.common.setFadeInOutAnimation
import com.mo2o.template.infrastructure.ui.common.setSlideRightAnimation
import kategory.Option
import kotlinx.android.synthetic.main.toolbar.*

const val login: String = "LOGIN"
const val repository: String = "REPOSITORY"
const val path: String = "PATH"
const val pass: String = "PASS"
const val name: String = "NAME"

inline fun <reified T : Fragment> create(args: List<Pair<String, Command>> = listOf()): T {
    val fragment = getFragment(T::class.java)
    val bundle = Bundle()
    args.map { bundle.putSerializable(it.first, it.second) }
    fragment.arguments = bundle
    return fragment
}

fun <T> getFragment(c: Class<T>) = c.newInstance()

inline fun <reified T : Fragment> AppCompatActivity.loadFragment(args: List<Pair<String, Command>> = listOf()) {
    supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.container, create<T>(args), T::class.java.simpleName)
            .commit()
}

fun Fragment.setToolbar(title: Int) = (activity as AppCompatActivity).setToolbar(title)

fun AppCompatActivity.setToolbar(title: Int) {
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.title = ""
    tvTitle.text = getString(title)
}

fun Fragment.gridLayoutManager(cels: Int = 2) = GridLayoutManager(context, cels)
fun Fragment.linearLayoutManager() = LinearLayoutManager(context)

fun <E : Command> Fragment.getArg(extra: String): Option<E> = arguments?.getSerializable(extra)?.let { Option(it as E) } ?: Option.None

inline fun <reified T : Activity> Fragment.load(pairs: List<Pair<String, Command>> = listOf()) = with(activity) {
    goToActivity<T>(pairs)
    finish()
    setFadeInOutAnimation()
}

inline fun <reified T : Fragment> Fragment.loadFragment(args: List<Pair<String, Command>> = listOf()) {
    activity.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.container, create<T>(args), T::class.java.simpleName)
            .commit()
}
