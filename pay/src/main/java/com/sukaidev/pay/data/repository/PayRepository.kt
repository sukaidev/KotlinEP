package com.sukaidev.pay.data.repository

import com.sukaidev.common.data.net.RetrofitFactory
import com.sukaidev.common.data.protocol.BaseResp
import com.sukaidev.pay.data.api.PayApi
import com.sukaidev.pay.data.protocol.GetPaySignReq
import com.sukaidev.pay.data.protocol.PayOrderReq
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/19.
 * 支付数据层.
 */
class PayRepository @Inject constructor() {
    /**
     * 获取支付宝支付签名
     */
    fun getPaySign(orderId: Int, totalPrice: Long): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(PayApi::class.java)
            .getPaySign(GetPaySignReq(orderId, totalPrice))
    }

    /**
     * 刷新订单状态已支付
     */
    fun payOrder(orderId: Int): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(PayApi::class.java).payOrder(PayOrderReq(orderId))
    }
}