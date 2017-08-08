package com.mo2o.template.di

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.mo2o.template.Cache
import com.mo2o.template.SharedPreferencesCache
import com.mo2o.template.api.ServiceGenerator
import com.mo2o.template.api.TemplateService
import com.mo2o.template.ui.GlideLoader
import com.mo2o.template.ui.ImageLoader
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
                    cache.get("name", ""),
                    cache.get("pass", ""))

    @Singleton @Provides
    fun provideCache(context: Context): Cache = SharedPreferencesCache(context)

    @Provides
    @Singleton
    fun provideImageLoader(context: Context): ImageLoader = GlideLoader(Glide.with(context))


}