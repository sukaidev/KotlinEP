package com.sukaidev.common.injection.module

import android.content.Context
import com.sukaidev.common.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by sukai on 2019/08/10.
 *
 */
@Module
class AppModule(private val context: BaseApplication) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }
}