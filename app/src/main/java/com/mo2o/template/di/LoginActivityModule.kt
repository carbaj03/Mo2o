package com.mo2o.template.di

import com.mo2o.template.ui.LoginActivity
import com.mo2o.template.ui.MainActivity
import dagger.Module
import dagger.Provides


@Module
class LoginActivityModule {
    @Provides
    fun provideLoginView(loginActivity: LoginActivity) : LoginActivity = loginActivity

}