package com.sukaidev.goods.injection.component

import com.sukaidev.core.injection.PerComponentScope
import com.sukaidev.core.injection.component.ActivityComponent
import com.sukaidev.goods.injection.module.CartModule
import com.sukaidev.goods.ui.cart.ShopCartDelegate
import com.sukaidev.goods.ui.goods.GoodsInfoDelegate
import dagger.Component

/**
 * Created by sukaidev on 2019/08/16.
 * 购物车component.
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [CartModule::class])
interface CartComponent {
    fun inject(delegate: ShopCartDelegate)
    fun inject(delegate:GoodsInfoDelegate)
}
