package com.mo2o.template.di

import com.mo2o.template.ui.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = arrayOf(
            MainActivityModule::class, FragmentBuilderModule::class
    ))
    abstract fun contributeMainActivity(): MainActivity
}