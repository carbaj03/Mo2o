package com.mo2o.template.infrastructure.di

import com.mo2o.template.infrastructure.ui.MainActivity
import dagger.Module
import dagger.Provides


@Module
class MainActivityModule {
    @Provides
    fun provideMainView(mainActivity: MainActivity) : MainActivity = mainActivity

}