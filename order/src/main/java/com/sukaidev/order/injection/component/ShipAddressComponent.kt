package com.sukaidev.order.injection.component

import com.sukaidev.core.injection.PerComponentScope
import com.sukaidev.core.injection.component.ActivityComponent
import com.sukaidev.order.injection.module.ShipAddressModule
import com.sukaidev.order.ui.fragment.ShipAddressDelegate
import com.sukaidev.order.ui.fragment.ShipAddressEditDelegate
import dagger.Component

/**
 * Created by sukaidev on 2019/08/17.
 * 收货地址Component.
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [ShipAddressModule::class])
interface ShipAddressComponent {
    fun inject(delegate: ShipAddressDelegate)
    fun inject(delegate: ShipAddressEditDelegate)
}