package com.mo2o.template.infrastructure.di

import com.mo2o.template.infrastructure.ui.LoginActivity
import com.mo2o.template.infrastructure.ui.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = arrayOf(
            LoginActivityModule::class
    ))
    abstract fun contributeLoginActivity(): LoginActivity


    @ContributesAndroidInjector(modules = arrayOf(
            MainActivityModule::class, FragmentBuilderModule::class
    ))
    abstract fun contributeMainActivity(): MainActivity
}