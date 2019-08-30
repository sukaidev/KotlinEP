package com.sukaidev.user.injection.module

import com.sukaidev.user.service.UploadService
import com.sukaidev.user.service.impl.UploadServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
@Module
class UploadModule {

    @Provides
    fun providesUploadService(service: UploadServiceImpl): UploadService {
        return service
    }
}