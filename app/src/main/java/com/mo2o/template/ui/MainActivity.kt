package com.mo2o.template.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.mo2o.template.*
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setSupportActionBar(toolbar)
        setContentView(R.layout.activity_main)

        bnd.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                OVERVIEW -> Action { loadFragment<OverviewFragment>() }
                STARS -> Action { }
                REPOSITORIES -> Action { loadFragment<MainFragment>() }
                FOLLOWING -> Action { }
            }
            true
        }
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}