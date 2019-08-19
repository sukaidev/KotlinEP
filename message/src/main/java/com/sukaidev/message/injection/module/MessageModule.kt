package com.sukaidev.message.injection.module

import com.sukaidev.message.service.IMessageService
import com.sukaidev.message.service.impl.MessageServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
@Module
class MessageModule {

    @Provides
    fun providesMessageService(serviceImpl: MessageServiceImpl): IMessageService {
        return serviceImpl
    }
}