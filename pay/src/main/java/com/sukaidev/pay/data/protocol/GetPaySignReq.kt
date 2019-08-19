package com.sukaidev.pay.data.protocol

/**
 * Created by sukaidev on 2019/08/19.
 * 获取支付宝 支付签名
 */
data class GetPaySignReq(val orderId: Int, val totalPrice: Long)