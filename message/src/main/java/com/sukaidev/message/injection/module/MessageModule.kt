package com.sukaidev.message.injection.module

import com.sukaidev.message.service.MessageService
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
    fun providesMessageService(serviceImpl: MessageServiceImpl): MessageService {
        return serviceImpl
    }
}