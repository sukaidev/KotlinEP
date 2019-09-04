package com.sukaidev.order.data.repository

import com.sukaidev.core.data.net.RetrofitFactory
import com.sukaidev.core.data.protocol.BaseResp
import com.sukaidev.order.data.api.OrderApi
import com.sukaidev.order.data.protocol.*
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/17.
 * 订单数据层.
 */
class OrderRepository @Inject constructor() {

    /**
     * 提交订单
     */
    fun submitOrder(order: Order): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(OrderApi::class.java)
            .submitOrder(SubmitOrderReq(order))
    }

    /**
     * 确认订单
     */
    fun confirmOrder(orderId: Int): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(OrderApi::class.java).confirmOrder(ConfirmOrderReq(orderId))
    }

    /**
     * 取消订单
     */
    fun cancelOrder(orderId: Int): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(OrderApi::class.java).cancelOrder(CancelOrderReq(orderId))
    }

    /**
     * 根据ID查询订单
     */
    fun getOrderById(orderId: Int): Observable<BaseResp<Order>> {
        return RetrofitFactory.instance.create(OrderApi::class.java).getOrderById(GetOrderByIdReq(orderId))
    }

    /**
     * 根据状态查询订单
     */
    fun getOrderList(orderStatus: Int): Observable<BaseResp<MutableList<Order>?>> {
        return RetrofitFactory.instance.create(OrderApi::class.java).getOrderList(GetOrderListReq(orderStatus))
    }
}