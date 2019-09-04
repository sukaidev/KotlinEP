package com.sukaidev.order.presenter

import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.order.data.protocol.Order
import com.sukaidev.order.presenter.view.OrderListView
import com.sukaidev.order.service.OrderService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/18.
 *
 */
class OrderListPresenter @Inject constructor() : BasePresenter<OrderListView>() {

    @Inject
    lateinit var service: OrderService

    /**
     * 根据Id查询订单
     */
    fun getOrderList(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        service.getOrderList(orderId).execute(object : BaseSubscriber<MutableList<Order>?>(mView) {
            override fun onNext(t: MutableList<Order>?) {
                mView.onGetOrderListResult(t)
            }
        }, lifecycleProvider)
    }

    /**
     * 确认订单
     */
    fun confirmOrder(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        service.confirmOrder(orderId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onConfirmOrderResult(t)
            }
        }, lifecycleProvider)
    }

    /**
     * 取消订单
     */
    fun cancelOrder(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        service.cancelOrder(orderId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onCancelOrderResult(t)
            }
        }, lifecycleProvider)
    }

}