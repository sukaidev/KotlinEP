package com.sukaidev.order.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.order.data.protocol.Order
import com.sukaidev.order.presenter.view.IOrderListView
import com.sukaidev.order.service.IOrderService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/18.
 *
 */
class OrderListPresenter @Inject constructor() : BasePresenter<IOrderListView>() {

    @Inject
    lateinit var service: IOrderService

    /**
     * 根据Id查询订单
     */
    fun getOrderList(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
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
        mView.showLoading()
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
        mView.showLoading()
        service.cancelOrder(orderId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onCancelOrderResult(t)
            }
        }, lifecycleProvider)
    }

}