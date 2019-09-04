package com.sukaidev.order.presenter

import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.order.data.protocol.Order
import com.sukaidev.order.presenter.view.OrderDetailView
import com.sukaidev.order.service.OrderService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
class OrderDetailPresenter @Inject constructor() : BasePresenter<OrderDetailView>() {

    @Inject
    lateinit var service: OrderService

    /**
     * 根据Id获取订单详情
     */
    fun getOrderById(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        service.getOrderById(orderId).execute(object : BaseSubscriber<Order>(mView) {
            override fun onNext(t: Order) {
                mView.onGetOrderByIdResult(t)
            }
        },lifecycleProvider)
    }
}