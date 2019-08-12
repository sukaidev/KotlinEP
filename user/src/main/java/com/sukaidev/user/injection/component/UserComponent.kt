package com.sukaidev.user.injection.component

import com.sukaidev.common.injection.PerComponentScope
import com.sukaidev.common.injection.component.ActivityComponent
import com.sukaidev.user.injection.module.UserModule
import com.sukaidev.user.ui.activity.*
import dagger.Component

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [UserModule::class])
interface UserComponent {
    fun inject(activity: LoginActivity)
    fun inject(activity: RegisterActivity)
    fun inject(activity: ForgetPwdActivity)
    fun inject(activity: ResetPwdActivity)
    fun inject(activity: UserInfoActivity)

}