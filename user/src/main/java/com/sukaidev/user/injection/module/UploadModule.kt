package com.sukaidev.user.injection.module

import com.sukaidev.user.service.UploadService
import com.sukaidev.user.service.UserService
import com.sukaidev.user.service.impl.UploadServiceImpl
import com.sukaidev.user.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by sukai on 2019/08/10.
 *
 */
@Module
class UploadModule {

    @Provides
    fun providesUploadService(service: UploadServiceImpl): UploadService {
        return service
    }
}