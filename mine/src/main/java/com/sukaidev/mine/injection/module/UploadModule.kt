package com.sukaidev.mine.injection.module

import com.sukaidev.mine.service.UploadService
import com.sukaidev.mine.service.impl.UploadServiceImpl
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