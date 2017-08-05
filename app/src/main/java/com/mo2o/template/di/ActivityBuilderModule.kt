package com.mo2o.template.di

import com.mo2o.template.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by mertsimsek on 25/05/2017.
 */
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    internal abstract fun bindMainActivity(): MainActivity

}