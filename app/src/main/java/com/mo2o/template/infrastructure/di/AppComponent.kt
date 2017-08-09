package com.mo2o.template.infrastructure.di

import android.app.Application
import com.mo2o.template.infrastructure.TemplateApp
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
}
