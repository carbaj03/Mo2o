package com.mo2o.template

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), LifecycleRegistryOwner, HasSupportFragmentInjector {
    private val lifecycleRegistry = LifecycleRegistry(this)
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showUser()
    }

    private fun showUser() = loadFragment<MainFragment>()

    override fun getLifecycle() = lifecycleRegistry

    override fun supportFragmentInjector() = dispatchingAndroidInjector

}