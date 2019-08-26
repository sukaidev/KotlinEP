package com.sukaidev.core.injection.component

import android.content.Context
import com.sukaidev.core.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by sukai on 2019/08/10.
 *
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun context(): Context
}