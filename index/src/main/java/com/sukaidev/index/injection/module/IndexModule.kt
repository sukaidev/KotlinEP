package com.sukaidev.index.injection.module

import com.sukaidev.index.service.IndexService
import com.sukaidev.index.service.impl.IndexServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by sukaidev on 2019/08/27.
 *
 */
@Module
class IndexModule {

    @Provides
    fun getProvidesService(service: IndexServiceImpl): IndexService {
        return service
    }
}