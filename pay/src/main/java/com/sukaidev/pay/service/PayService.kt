package com.sukaidev.pay.service

import rx.Observable

/**
 * Created by sukaidev on 2019/08/19.
 * 支付  业务接口
 */
interface PayService {

    /**
     * 获取支付宝支付签名
     */
    fun getPaySign(orderId: Int, totalPrice: Long): Observable<String>

    /**
     * 刷新订单状态已支付
     */
    fun payOrder(orderId: Int):Observable<Boolean>
}