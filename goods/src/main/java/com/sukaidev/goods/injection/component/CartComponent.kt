package com.sukaidev.goods.injection.component

import com.sukaidev.common.injection.PerComponentScope
import com.sukaidev.common.injection.component.ActivityComponent
import com.sukaidev.goods.injection.module.CartModule
import dagger.Component

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [CartModule::class])
interface CartComponent