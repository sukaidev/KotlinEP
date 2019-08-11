package com.sukaidev.user.injection.component

import com.sukaidev.common.injection.PerComponentScope
import com.sukaidev.common.injection.component.ActivityComponent
import com.sukaidev.user.injection.module.UserModule
import com.sukaidev.user.ui.activity.RegisterActivity
import dagger.Component

/**
 * Created by sukai on 2019/08/10.
 *
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [UserModule::class])
interface UserComponent {
    fun inject(activity: RegisterActivity)
}