package com.sukaidev.order.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.order.injection.component.DaggerOrderComponent
import com.sukaidev.order.injection.module.OrderModule
import com.sukaidev.order.presenter.OrderConfirmPresenter
import com.sukaidev.order.presenter.view.IOrderConfirmView
import com.sukaidev.order.ui.adapter.OrderAdapter
import com.sukaidev.provider.router.RouterPath

/**
 * Created by sukaidev on 2019/08/17.
 *
 */
@Route(path = RouterPath.Order.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : BaseMvpActivity<OrderConfirmPresenter>(), IOrderConfirmView {

    lateinit var mAdapter: OrderAdapter

    override fun injectComponent() {
        DaggerOrderComponent
            .builder()
            .activityComponent(activityComponent)
            .orderModule(OrderModule())
            .build()
            .inject(this)
    }

    override fun setLayout(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindView(savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}