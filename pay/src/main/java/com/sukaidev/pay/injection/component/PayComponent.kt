package com.sukaidev.pay.injection.component

import com.sukaidev.core.injection.PerComponentScope
import com.sukaidev.core.injection.component.ActivityComponent
import com.sukaidev.pay.injection.module.PayModule
import dagger.Component

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [PayModule::class])
interface PayComponent {
//    fun inject(activity: CashRegisterActivity)
}