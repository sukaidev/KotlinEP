package com.sukaidev.goods.injection.component

import com.sukaidev.core.injection.PerComponentScope
import com.sukaidev.core.injection.component.ActivityComponent
import com.sukaidev.goods.injection.module.CartModule
import com.sukaidev.goods.injection.module.GoodsModule
import com.sukaidev.goods.ui.fragment.GoodsDelegate
import dagger.Component

/**
 * Created by sukaidev on 2019/08/15.
 * 商品Component.
 */
@PerComponentScope
@Component(
    dependencies = [ActivityComponent::class],
    modules = [GoodsModule::class, CartModule::class]
)
interface GoodsComponent {
    fun inject(delegate: GoodsDelegate)
//    fun inject(fragment: GoodsDetailTabOneFragment)
}