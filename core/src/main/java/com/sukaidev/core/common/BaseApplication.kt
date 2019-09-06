package com.sukaidev.core.common

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.sukaidev.core.ext.notNullSingleValue
import com.sukaidev.core.injection.component.AppComponent
import com.sukaidev.core.injection.component.DaggerAppComponent
import com.sukaidev.core.injection.module.AppModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger.addLogAdapter

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
open class BaseApplication : Application() {

    protected var isDebug: Boolean = true

    lateinit var appComponent: AppComponent

    companion object {
        var instance: Application by notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initAppInjection()

        if (isDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)

            // 初始化Logger
            addLogAdapter(AndroidLogAdapter())
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}