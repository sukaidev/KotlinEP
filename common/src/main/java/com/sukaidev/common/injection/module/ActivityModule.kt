package com.sukaidev.common.injection.module

import android.app.Activity
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
class ActivityModule(private val activity: Activity) {

    @Provides
    fun providesActivity(): Activity {
        return activity
    }
}