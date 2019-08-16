package com.sukaidev.common.ui.activity

import android.os.Bundle
import com.sukaidev.common.common.AppManager
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
open class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.INSTANCE.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.INSTANCE.finishActivity(this)
    }


}