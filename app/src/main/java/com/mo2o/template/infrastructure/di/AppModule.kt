package com.mo2o.template.infrastructure.di

import android.app.Application
import android.content.Context
import com.mo2o.template.infrastructure.persistence.Cache
import com.mo2o.template.infrastructure.persistence.SharedPreferencesCache
import com.mo2o.template.infrastructure.api.ServiceGenerator
import com.mo2o.template.infrastructure.api.TemplateService
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
    fun providesService(cache: Cache): TemplateService =
            ServiceGenerator.createService(
                    TemplateService::class.java,
                    cache.get("login", ""),
                    cache.get("pass", ""))

    @Singleton @Provides
    fun provideCache(context: Context): Cache = SharedPreferencesCache(context)
}