package com.mo2o.template.infrastructure.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.mo2o.template.*
import com.mo2o.template.infrastructure.extension.*
import com.mo2o.template.infrastructure.ui.following.FollowingFragment
import com.mo2o.template.infrastructure.ui.overview.OverviewFragment
import com.mo2o.template.infrastructure.ui.repository.RepositoryFragment
import com.mo2o.template.infrastructure.ui.starred.StarredFragment
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

        loadFragment<OverviewFragment>()
        bnd.setOnNavigationItemSelectedListener {
            Action {
                when (it.itemId) {
                    OVERVIEW -> Action { loadFragment<OverviewFragment>() }
                    STARS -> Action { loadFragment<StarredFragment>() }
                    REPOSITORIES -> Action { loadFragment<RepositoryFragment>() }
                    FOLLOWING -> Action { loadFragment<FollowingFragment>() }
                }
            }
        }
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}