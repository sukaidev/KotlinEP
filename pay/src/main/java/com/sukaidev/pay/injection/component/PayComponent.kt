package com.sukaidev.pay.injection.component

import com.sukaidev.common.injection.PerComponentScope
import com.sukaidev.common.injection.component.ActivityComponent
import com.sukaidev.pay.injection.module.PayModule
import com.sukaidev.pay.ui.activity.CashRegisterActivity
import dagger.Component

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [PayModule::class])
interface PayComponent {
    fun inject(activity: CashRegisterActivity)
}