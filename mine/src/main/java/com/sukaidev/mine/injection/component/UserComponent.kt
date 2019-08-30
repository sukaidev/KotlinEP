package com.sukaidev.mine.injection.component

import com.sukaidev.core.injection.PerComponentScope
import com.sukaidev.core.injection.component.ActivityComponent
import com.sukaidev.mine.injection.module.UploadModule
import com.sukaidev.mine.injection.module.UserModule
import com.sukaidev.mine.ui.user.*
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