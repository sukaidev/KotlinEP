package com.sukaidev.pay.service.impl

import com.sukaidev.core.ext.convert
import com.sukaidev.core.ext.convertBoolean
import com.sukaidev.pay.data.repository.PayRepository
import com.sukaidev.pay.service.IPayService
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
class PayServiceImpl @Inject constructor() : IPayService {

    @Inject
    lateinit var repository: PayRepository

    override fun getPaySign(orderId: Int, totalPrice: Long): Observable<String> {
        return repository.getPaySign(orderId, totalPrice).convert()
    }

    override fun payOrder(orderId: Int): Observable<Boolean> {
        return repository.payOrder(orderId).convertBoolean()
    }
}