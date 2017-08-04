package com.mo2o.template

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

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
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
            .replace(R.id.container, create<T>(args), T::class.java.simpleName)
            .commit()
}

