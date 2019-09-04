package com.sukaidev.order.presenter

import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.order.data.protocol.Order
import com.sukaidev.order.presenter.view.OrderConfirmView
import com.sukaidev.order.service.OrderService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/17.
 *
 */
class OrderConfirmPresenter @Inject constructor() : BasePresenter<OrderConfirmView>() {

    @Inject
    lateinit var service: OrderService

    /**
     * 根据Id查询订单
     */
    fun getOrderById(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        service.getOrderById(orderId).execute(object : BaseSubscriber<Order>(mView) {
            override fun onNext(t: Order) {
                mView.onGetOrderByIdResult(t)
            }
        }, lifecycleProvider)
    }

    /**
     * 提交订单
     */
    fun submitOrder(order: Order) {
        if (!checkNetWork()) {
            return
        }
        service.submitOrder(order).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onSubmitOrderResult(t)
            }
        }, lifecycleProvider)
    }

}