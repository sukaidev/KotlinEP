package com.sukaidev.core.ui.delegates

import android.os.Bundle
import android.widget.Toast
import com.sukaidev.core.common.BaseApplication
import com.sukaidev.core.injection.component.ActivityComponent
import com.sukaidev.core.injection.component.DaggerActivityComponent
import com.sukaidev.core.injection.module.ActivityModule
import com.sukaidev.core.injection.module.LifecycleProviderModule
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.presenter.view.BaseView
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/26.
 *
 */
abstract class BaseMvpDelegate<T : BasePresenter<*>> : ProxyDelegate(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var mActivityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()
    }

    abstract fun injectComponent()

    private fun initActivityInjection() {
        mActivityComponent =
            DaggerActivityComponent.builder()
                .appComponent((_mActivity.application as BaseApplication).appComponent)
                .activityModule(
                    ActivityModule(_mActivity)
                ).lifecycleProviderModule(LifecycleProviderModule(this)).build()
    }

    override fun onError(msg: String) {
        _mActivity.toast(msg)
    }

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