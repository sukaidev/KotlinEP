package com.sukaidev.user.injection.module

import com.sukaidev.user.service.IUploadService
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
    fun providesUploadService(service: UploadServiceImpl): IUploadService {
        return service
    }
}