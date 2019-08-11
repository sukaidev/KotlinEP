package com.sukaidev.common.common

import android.app.Application
import com.sukaidev.common.injection.component.AppComponent
import com.sukaidev.common.injection.component.DaggerAppComponent
import com.sukaidev.common.injection.module.AppModule

/**
 * Created by sukai on 2019/08/10.
 *
 */
class BaseApplication : Application() {

     lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initAppInjection()
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}