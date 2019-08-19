package com.sukaidev.kotlinmall.common

import cn.jpush.android.api.JPushInterface
import com.sukaidev.common.common.BaseApplication

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
class MainApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
    }
}