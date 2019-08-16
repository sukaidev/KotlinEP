package com.sukaidev.goods.injection.component

import com.sukaidev.common.injection.PerComponentScope
import com.sukaidev.common.injection.component.ActivityComponent
import com.sukaidev.goods.injection.module.GoodsModule
import com.sukaidev.goods.ui.activity.GoodsActivity
import com.sukaidev.goods.ui.fragment.GoodsDetailTabOneFragment
import dagger.Component

/**
 * Created by sukaidev on 2019/08/15.
 * 商品Component.
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [GoodsModule::class])
interface GoodsComponent {
    fun inject(activity: GoodsActivity)
    fun inject(fragment: GoodsDetailTabOneFragment)
}