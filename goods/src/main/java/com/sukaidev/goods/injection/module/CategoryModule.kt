package com.sukaidev.goods.injection.module

import com.sukaidev.goods.service.ICategoryService
import com.sukaidev.goods.service.impl.CategoryServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by sukaidev on 2019/08/15.
 * 商品分类Module.
 */
@Module
class CategoryModule {

    @Provides
    fun providesCategoryService(service: CategoryServiceImpl): ICategoryService {
        return service
    }
}