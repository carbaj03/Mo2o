package com.mo2o.template.di

import com.mo2o.template.ui.FollowingFragment
import com.mo2o.template.ui.MainFragment
import com.mo2o.template.ui.OverviewFragment
import com.mo2o.template.ui.StarredFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeOverviewFragment(): OverviewFragment

    @ContributesAndroidInjector
    abstract fun contributeStarredFragment(): StarredFragment

    @ContributesAndroidInjector
    abstract fun contributeFollowingFragment(): FollowingFragment

}
