package com.sukaidev.index.injection.component

import com.sukaidev.core.injection.PerComponentScope
import com.sukaidev.core.injection.component.ActivityComponent
import com.sukaidev.index.injection.module.IndexModule
import com.sukaidev.index.ui.fragment.IndexDelegate
import dagger.Component

/**
 * Created by sukaidev on 2019/08/27.
 *
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [IndexModule::class])
interface IndexComponent {
    fun inject(delegate: IndexDelegate)
}