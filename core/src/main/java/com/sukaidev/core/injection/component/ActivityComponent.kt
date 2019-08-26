package com.sukaidev.core.injection.component

import android.app.Activity
import android.content.Context
import com.sukaidev.core.injection.ActivityScope
import com.sukaidev.core.injection.component.AppComponent
import com.sukaidev.core.injection.module.ActivityModule
import com.sukaidev.core.injection.module.LifecycleProviderModule
import com.trello.rxlifecycle.LifecycleProvider
import dagger.Component

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