package com.sukaidev.common.injection.component

import android.app.Activity
import android.content.Context
import com.sukaidev.common.injection.ActivityScope
import com.sukaidev.common.injection.module.ActivityModule
import com.sukaidev.common.injection.module.AppModule
import com.sukaidev.common.injection.module.LifecycleProviderModule
import com.trello.rxlifecycle.LifecycleProvider
import dagger.Component
import javax.inject.Singleton

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class, LifecycleProviderModule::class])
interface ActivityComponent {
    fun activity(): Activity
    fun context(): Context
    fun lifecycleProvider(): LifecycleProvider<*>
}