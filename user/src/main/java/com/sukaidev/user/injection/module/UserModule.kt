package com.sukaidev.user.injection.module

import com.sukaidev.user.service.IUserService
import com.sukaidev.user.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by sukai on 2019/08/10.
 *
 */
@Module
class UserModule {

    @Provides
    fun providesUserService(service: UserServiceImpl): IUserService {
        return service
    }
}