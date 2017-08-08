package com.mo2o.template.di

import android.app.Application
import com.mo2o.template.TemplateApp
import com.mo2o.template.ui.ImageLoader
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        ActivityBuilderModule::class,
        AppModule::class
))
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: TemplateApp)

    fun getImageLoader(): ImageLoader
}
