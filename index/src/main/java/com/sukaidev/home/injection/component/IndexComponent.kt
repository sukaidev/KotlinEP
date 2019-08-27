package com.sukaidev.home.injection.component

import com.sukaidev.core.injection.PerComponentScope
import com.sukaidev.core.injection.component.ActivityComponent
import com.sukaidev.home.ui.fragment.IndexDelegate
import dagger.Component

/**
 * Created by sukaidev on 2019/08/27.
 *
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class])
interface IndexComponent {
    fun inject(delegate :IndexDelegate)
}