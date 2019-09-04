package com.sukaidev.order.injection.component

import com.sukaidev.core.injection.PerComponentScope
import com.sukaidev.core.injection.component.ActivityComponent
import com.sukaidev.order.injection.module.OrderModule
import com.sukaidev.order.ui.fragment.OrderConfirmDelegate
import com.sukaidev.order.ui.fragment.OrderDelegate
import com.sukaidev.order.ui.fragment.OrderDetailDelegate
import dagger.Component

/**
 * Created by sukaidev on 2019/08/17.
 * 订单Component.
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [OrderModule::class])
interface OrderComponent {
    fun inject(delegate: OrderConfirmDelegate)
    fun inject(delegate: OrderDelegate)
    fun inject(delegate: OrderDetailDelegate)
}