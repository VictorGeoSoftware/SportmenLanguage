package com.victor.test.sport.di

import android.app.Application
import com.victor.test.sport.ui.main.MainActivityModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, PresenterModule::class])
interface AppComponent {
    fun inject(application: Application)
    fun plus(mainActivityModule: MainActivityModule): MainActivityComponent
}