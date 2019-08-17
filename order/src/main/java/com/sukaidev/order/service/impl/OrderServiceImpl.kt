package com.sukaidev.order.service.impl

import com.sukaidev.common.ext.convert
import com.sukaidev.common.ext.convertBoolean
import com.sukaidev.order.data.protocol.Order
import com.sukaidev.order.data.repository.OrderRepository
import com.sukaidev.order.service.IOrderService
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/17.
 * 订单业务实现.
 */
class OrderServiceImpl @Inject constructor() : IOrderService {

    @Inject
    lateinit var repository: OrderRepository

    override fun submitOrder(order: Order): Observable<String> {
        return repository.submitOrder(order).convert()
    }

    override fun confirmOrder(orderId: Int): Observable<Boolean> {
        return repository.confirmOrder(orderId).convertBoolean()
    }

    override fun cancelOrder(orderId: Int): Observable<Boolean> {
        return repository.cancelOrder(orderId).convertBoolean()
    }

    override fun getOrderById(orderId: Int): Observable<Order> {
        return repository.getOrderById(orderId).convert()
    }

    override fun getOrderList(orderStatus: Int): Observable<MutableList<Order>?> {
        return repository.getOrderList(orderStatus).convert()
    }
}