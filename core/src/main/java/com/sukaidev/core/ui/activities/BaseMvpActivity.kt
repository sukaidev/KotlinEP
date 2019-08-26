package com.sukaidev.core.ui.activities

import android.os.Bundle
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
 * Created by sukaidev on 2019/08/25.
 *
 */
abstract class BaseMvpActivity<T : BasePresenter<*>> : ProxyActivity(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var mActivityComponent: ActivityComponent

    /**
     * 设置布局ID
     */
    abstract fun setLayout(): Int

    /**
     * 初始化视图
     */
    abstract fun onBindView(savedInstanceState: Bundle?)

    /**
     * 初始化依赖注入组件
     * 注意：mPresenter中的mView也需要在这里进行初始化!
     */
    abstract fun injectComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        initActivityInjection()
        injectComponent()

        onBindView(savedInstanceState)
    }

    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
            .appComponent((application as BaseApplication).appComponent)
            .activityModule(ActivityModule(this))
            .lifecycleProviderModule(LifecycleProviderModule(this))
            .build()
    }

    override fun onError(msg: String) {
        toast(msg)
    }
}