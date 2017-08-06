package com.mo2o.template.ui


import android.support.v4.app.Fragment
import com.mo2o.template.R
import com.mo2o.template.setToolbar
import dagger.android.support.AndroidSupportInjection


class MainFragment : BaseFragment() {
    override fun getLayout() = R.layout.fragment_main

    override fun onCreate() {
        setToolbar(R.string.main)
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        AndroidSupportInjection.inject(this)
    }
}