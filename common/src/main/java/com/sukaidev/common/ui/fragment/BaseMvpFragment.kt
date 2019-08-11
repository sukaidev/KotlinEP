package com.sukaidev.common.ui.fragment

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.sukaidev.common.common.BaseApplication
import com.sukaidev.common.injection.component.ActivityComponent
import com.sukaidev.common.injection.component.DaggerActivityComponent
import com.sukaidev.common.injection.module.ActivityModule
import com.sukaidev.common.injection.module.LifecycleProviderModule
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.presenter.view.BaseView
import javax.inject.Inject

/**
 * Created by sukai on 2019/08/10.
 *
 */
abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var activityComponent: ActivityComponent

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(msg: String) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()
    }

    abstract fun injectComponent()

    private fun initActivityInjection() {
        activityComponent =
                DaggerActivityComponent.builder().appComponent((activity?.application as BaseApplication).appComponent)
                        .activityModule(
                                ActivityModule(activity as FragmentActivity)
                        ).lifecycleProviderModule(LifecycleProviderModule(this)).build()
    }
}