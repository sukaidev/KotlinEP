package com.sukaidev.order.data.protocol

/**
 * Created by sukaidev on 2019/08/17.
 * 添加收货地址请求实体.
 */
data class AddShipAddressReq(val shipUserName: String, val shipUserMobile: String, val shipAddress: String)