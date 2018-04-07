package com.victor.test.sport

import android.app.Application
import com.victor.test.sport.di.AppComponent
import com.victor.test.sport.di.AppModule
import com.victor.test.sport.di.DaggerAppComponent

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */
class MainApplication: Application() {

    val component: AppComponent by lazy { DaggerAppComponent.builder().appModule(AppModule(this)).build() }

    override fun onCreate() {
        super.onCreate()

        component.inject(this)
    }
}