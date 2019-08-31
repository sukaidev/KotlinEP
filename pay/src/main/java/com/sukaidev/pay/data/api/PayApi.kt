package com.sukaidev.pay.data.api

import com.sukaidev.core.data.protocol.BaseResp
import com.sukaidev.pay.data.protocol.GetPaySignReq
import com.sukaidev.pay.data.protocol.PayOrderReq
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * Created by sukaidev on 2019/08/19.
 * 支付相关API.
 */
interface PayApi {

    /**
     * 获取支付宝支付签名
     */
    @POST("pay/getPaySign")
    fun getPaySign(@Body req: GetPaySignReq): Observable<BaseResp<String>>

    /**
     * 刷新订单状态，已支付
     */
    @POST("order/pay")
    fun payOrder(@Body req: PayOrderReq): Observable<BaseResp<String>>
}