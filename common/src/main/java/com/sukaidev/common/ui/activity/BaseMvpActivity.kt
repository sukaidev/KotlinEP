package com.sukaidev.common.ui.activity

import android.os.Bundle
import com.sukaidev.common.common.BaseApplication
import com.sukaidev.common.injection.component.ActivityComponent
import com.sukaidev.common.injection.component.DaggerActivityComponent
import com.sukaidev.common.injection.module.ActivityModule
import com.sukaidev.common.injection.module.LifecycleProviderModule
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.presenter.view.BaseView
import com.sukaidev.common.widget.ProgressLoading
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
abstract class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var mActivityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading

    /**
     * 设置布局ID
     */
    abstract fun setLayout(): Int

    /**
     * 布局填充后的逻辑
     */
    abstract fun onBindView(savedInstanceState: Bundle?)

    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    override fun onError(msg: String) {
        toast(msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        initActivityInjection()
        injectComponent()
        mLoadingDialog = ProgressLoading.create(this)

        onBindView(savedInstanceState)
    }

    /**
     * 初始化依赖注入组件
     * 注意：mPresenter中的mView也需要在这里进行初始化!
     */
    abstract fun injectComponent()

    private fun initActivityInjection() {
        mActivityComponent =
            DaggerActivityComponent.builder()
                .appComponent((application as BaseApplication).appComponent)
                .activityModule(
                    ActivityModule(this)
                ).lifecycleProviderModule(LifecycleProviderModule(this)).build()
    }
}