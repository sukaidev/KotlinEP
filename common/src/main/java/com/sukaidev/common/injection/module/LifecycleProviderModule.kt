package com.sukaidev.common.injection.module

import android.app.Activity
import android.content.Context
import com.sukaidev.common.common.BaseApplication
import com.trello.rxlifecycle.LifecycleProvider
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by sukai on 2019/08/10.
 *
 */
@Module
class LifecycleProviderModule(private val lifecycleProvider: LifecycleProvider<*>) {

    @Provides
    fun providesLifecycleProvider(): LifecycleProvider<*> {
        return lifecycleProvider
    }
}