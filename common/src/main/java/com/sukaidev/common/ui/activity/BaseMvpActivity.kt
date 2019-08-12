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
 * Created by sukai on 2019/08/10.
 *
 */
abstract class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var activityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading

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
        initActivityInjection()
        injectComponent()

        mLoadingDialog = ProgressLoading.create(this)
    }

    abstract fun injectComponent()

    private fun initActivityInjection() {
        activityComponent =
                DaggerActivityComponent.builder().appComponent((application as BaseApplication).appComponent)
                        .activityModule(
                                ActivityModule(this)
                        ).lifecycleProviderModule(LifecycleProviderModule(this)).build()
    }
}