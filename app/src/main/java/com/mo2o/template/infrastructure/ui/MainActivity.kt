package com.mo2o.template.infrastructure.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.mo2o.template.Id
import com.mo2o.template.R
import com.mo2o.template.infrastructure.extension.*
import com.mo2o.template.infrastructure.persistence.Cache
import com.mo2o.template.infrastructure.ui.following.FollowingFragment
import com.mo2o.template.infrastructure.ui.overview.OverviewFragment
import com.mo2o.template.infrastructure.ui.content.RepositoryFragment
import com.mo2o.template.infrastructure.ui.starred.StarredFragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var preferences: Cache
    lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)

        setSupportActionBar(toolbar)

        tvTitle.text = "asdfdsf"
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        loadFragment<OverviewFragment>(listOf(extra to getExtra()))
        bnd.setOnNavigationItemSelectedListener {
            Action {
                when (it.itemId) {
                    OVERVIEW -> Action { loadFragment<OverviewFragment>(listOf(extra to getExtra())) }
                    STARS -> Action { loadFragment<StarredFragment>(listOf(extra to getExtra())) }
                    REPOSITORIES -> Action { loadFragment<RepositoryFragment>(listOf(extra to getExtra())) }
                    FOLLOWING -> Action { loadFragment<FollowingFragment>(listOf(extra to getExtra())) }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.make(R.menu.done, menu)
        menu.findItem(R.id.profile).isVisible = getExtra().value != preferences.get("login", "")
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        PROFILE -> Action { load<MainActivity>(listOf(extra to Id(preferences.get("login", "")))) }
        else -> super.onOptionsItemSelected(item)
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}

