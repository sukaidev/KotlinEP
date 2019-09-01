package com.sukaidev.goods.injection.component

import com.sukaidev.core.injection.PerComponentScope
import com.sukaidev.core.injection.component.ActivityComponent
import com.sukaidev.goods.injection.module.CategoryModule
import com.sukaidev.goods.ui.fragment.CategoryDelegate
//import com.sukaidev.goods.ui.fragment.CategoryDelegate
import dagger.Component

/**
 * Created by sukaidev on 2019/08/15.
 * 分类component.
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [CategoryModule::class])
interface CategoryComponent {
    fun inject(fragment: CategoryDelegate)
}