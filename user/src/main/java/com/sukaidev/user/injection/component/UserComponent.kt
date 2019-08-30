package com.sukaidev.user.injection.component

import com.sukaidev.core.injection.PerComponentScope
import com.sukaidev.core.injection.component.ActivityComponent
import com.sukaidev.user.injection.module.UploadModule
import com.sukaidev.user.injection.module.UserModule
import com.sukaidev.user.ui.fragment.*
import dagger.Component

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
@PerComponentScope
@Component(
    dependencies = [ActivityComponent::class],
    modules = [UserModule::class, UploadModule::class]
)
interface UserComponent {
    fun inject(delegate: LoginDelegate)
    fun inject(delegate: RegisterDelegate)
    fun inject(delegate: ForgetPwdDelegate)
    fun inject(delegate: ResetPwdDelegate)
    fun inject(delegate: UserInfoDelegate)
}