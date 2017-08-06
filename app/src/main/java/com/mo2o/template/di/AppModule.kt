package com.mo2o.template.di

import android.app.Application
import android.content.Context
import com.mo2o.template.api.TemplateService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideGithubService(): Retrofit =
            Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    @Provides @Singleton
    fun providesService(retrofit: Retrofit): TemplateService =
            retrofit.create(TemplateService::class.java)
}