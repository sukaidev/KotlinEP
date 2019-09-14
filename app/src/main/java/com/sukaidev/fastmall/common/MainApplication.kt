package com.sukaidev.fastmall.common

import cn.jpush.android.api.JPushInterface
import com.sukaidev.core.common.BaseApplication

/**
 * Created by sukaidev on 2019/08/27.
 *
 */

class MainApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        if (isDebug){
            JPushInterface.setDebugMode(true)
    }
        JPushInterface.init(this)
    }
}