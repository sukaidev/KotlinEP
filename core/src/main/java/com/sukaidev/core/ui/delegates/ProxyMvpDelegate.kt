package com.sukaidev.core.ui.delegates

import android.widget.Toast
import com.sukaidev.core.presenter.BasePresenter

/**
 * Created by sukaidev on 2019/08/30.
 * 由Root Fragment继承，实现双击退出.
 */
abstract class ProxyMvpDelegate<T : BasePresenter<*>> : BaseMvpDelegate<T>() {

    private val WAIT_TIME = 2000L
    private var TOUCH_TIME: Long = 0

    override fun onBackPressedSupport(): Boolean {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish()
        } else {
            TOUCH_TIME = System.currentTimeMillis()
            Toast.makeText(context, "双击退出", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}