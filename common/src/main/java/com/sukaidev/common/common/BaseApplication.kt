package com.sukaidev.common.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.sukaidev.common.injection.component.AppComponent
import com.sukaidev.common.injection.component.DaggerAppComponent
import com.sukaidev.common.injection.module.AppModule

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
class BaseApplication : Application() {

    private var isDebug: Boolean = true

    lateinit var appComponent: AppComponent

    companion object {
        private var instance: Application? = null
        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()

        initAppInjection()

        instance = this

        if (isDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}