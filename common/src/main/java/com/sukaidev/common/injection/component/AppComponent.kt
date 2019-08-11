package com.sukaidev.common.injection.component

import android.content.Context
import com.sukaidev.common.injection.module.AppModule
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