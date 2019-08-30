package com.sukaidev.mine.injection.module

import com.sukaidev.mine.service.UserService
import com.sukaidev.mine.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
@Module
class UserModule {

    @Provides
    fun providesUserService(service: UserServiceImpl): UserService {
        return service
    }
}