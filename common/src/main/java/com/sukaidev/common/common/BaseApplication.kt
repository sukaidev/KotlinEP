package com.sukaidev.common.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.sukaidev.common.injection.component.AppComponent
import com.sukaidev.common.injection.component.DaggerAppComponent
import com.sukaidev.common.injection.module.AppModule

/**
 * Created by sukai on 2019/08/10.
 *
 */
class BaseApplication : Application() {

    lateinit var appComponent: AppComponent

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        initAppInjection()
        context = this
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}