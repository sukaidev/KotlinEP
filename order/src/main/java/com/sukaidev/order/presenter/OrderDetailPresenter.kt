package com.sukaidev.order.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.order.data.protocol.Order
import com.sukaidev.order.presenter.view.IOrderDetailView
import com.sukaidev.order.service.IOrderService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
class OrderDetailPresenter @Inject constructor() : BasePresenter<IOrderDetailView>() {

    @Inject
    lateinit var service: IOrderService

    /**
     * 根据Id获取订单详情
     */
    fun getOrderById(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        service.getOrderById(orderId).execute(object : BaseSubscriber<Order>(mView) {
            override fun onNext(t: Order) {
                mView.onGetOrderByIdResult(t)
            }
        },lifecycleProvider)
    }
}