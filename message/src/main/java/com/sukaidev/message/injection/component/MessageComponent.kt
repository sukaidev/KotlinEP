package com.sukaidev.message.injection.component

import com.sukaidev.common.injection.PerComponentScope
import com.sukaidev.common.injection.component.ActivityComponent
import com.sukaidev.message.injection.module.MessageModule
import com.sukaidev.message.ui.fragment.MessageFragment
import dagger.Component

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [MessageModule::class])
interface MessageComponent {
    fun inject(fragment: MessageFragment)
}