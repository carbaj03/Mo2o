package com.mo2o.template.infrastructure.di

import com.mo2o.template.infrastructure.ui.LoginActivity
import dagger.Module
import dagger.Provides


@Module
class LoginActivityModule {
    @Provides
    fun provideLoginView(loginActivity: LoginActivity) : LoginActivity = loginActivity

}