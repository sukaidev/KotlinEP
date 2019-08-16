package com.sukaidev.goods.data.protocol

/**
 * Created by sukaidev on 2019/08/16.
 * 提交购物车.
 */
data class SubmitCartReq(val goodsList: List<CartGoods>, val totalPrice: Long)