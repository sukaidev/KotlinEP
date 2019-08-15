package com.sukaidev.goods.injection.component

import com.sukaidev.common.injection.PerComponentScope
import com.sukaidev.common.injection.component.ActivityComponent
import com.sukaidev.goods.injection.module.CategoryModule
import com.sukaidev.goods.ui.fragment.CategoryFragment
import dagger.Component

/**
 * Created by sukaidev on 2019/08/15.
 *
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [CategoryModule::class])
interface CategoryComponent {
    fun inject(fragment: CategoryFragment)
}