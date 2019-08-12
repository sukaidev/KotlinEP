package com.sukaidev.common.ui.activity

import android.os.Bundle
import com.sukaidev.common.common.AppManager
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

/**
 * Created by sukai on 2019/08/10.
 *
 */
abstract class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        AppManager.INSTANCE.addActivity(this)
        onBindView(savedInstanceState)
    }

    /**
     * 设置布局ID
     */
    abstract fun setLayout(): Int

    /**
     * 布局填充后的逻辑
     */
    abstract fun onBindView(savedInstanceState: Bundle?)

    override fun onDestroy() {
        super.onDestroy()
        AppManager.INSTANCE.finishActivity(this)
    }
}