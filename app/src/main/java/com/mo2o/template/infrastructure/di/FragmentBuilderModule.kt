package com.mo2o.template.infrastructure.di

import com.mo2o.template.infrastructure.ui.following.FollowingFragment
import com.mo2o.template.infrastructure.ui.repository.RepositoryFragment
import com.mo2o.template.infrastructure.ui.overview.OverviewFragment
import com.mo2o.template.infrastructure.ui.starred.StarredFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): RepositoryFragment

    @ContributesAndroidInjector
    abstract fun contributeOverviewFragment(): OverviewFragment

    @ContributesAndroidInjector
    abstract fun contributeStarredFragment(): StarredFragment

    @ContributesAndroidInjector
    abstract fun contributeFollowingFragment(): FollowingFragment

}
