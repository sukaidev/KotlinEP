package com.sukaidev.order.injection.component

import com.sukaidev.common.injection.PerComponentScope
import com.sukaidev.common.injection.component.ActivityComponent
import com.sukaidev.order.injection.module.ShipAddressModule
import com.sukaidev.order.ui.activity.ShipAddressActivity
import com.sukaidev.order.ui.activity.ShipAddressEditActivity
import dagger.Component

/**
 * Created by sukaidev on 2019/08/17.
 * 收货地址Component.
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [ShipAddressModule::class])
interface ShipAddressComponent {

    fun inject(activity: ShipAddressActivity)
    fun inject(activity: ShipAddressEditActivity)
}