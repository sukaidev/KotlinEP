package com.sukaidev.core.injection.module

import android.content.Context
import com.sukaidev.core.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by sukaidev on 2019/08/10.
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