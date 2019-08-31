package com.sukaidev.goods.injection.module

import com.sukaidev.goods.service.CartService
import com.sukaidev.goods.service.impl.CartServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
@Module
class CartModule {

    @Provides
    fun providesCartService(service: CartServiceImpl): CartService {
        return service
    }
}