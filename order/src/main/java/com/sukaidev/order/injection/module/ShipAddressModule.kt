package com.sukaidev.order.injection.module

import com.sukaidev.order.service.IShipAddressService
import com.sukaidev.order.service.impl.ShipAddressServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by sukaidev on 2019/08/17.
 *
 */
@Module
class ShipAddressModule {

    @Provides
    fun providesShipAddressService(service: ShipAddressServiceImpl): IShipAddressService {
        return service
    }
}