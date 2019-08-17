package com.sukaidev.order.service

import com.sukaidev.order.data.protocol.Order
import rx.Observable

/**
 * Created by sukaidev on 2019/08/17.
 *
 */
interface IOrderService {

    fun submitOrder(order: Order): Observable<String>

    fun confirmOrder(orderId: Int): Observable<Boolean>

    fun cancelOrder(orderId: Int): Observable<Boolean>

    fun getOrderById(orderId: Int): Observable<Order>

    fun getOrderList(orderStatus: Int): Observable<MutableList<Order>?>
}