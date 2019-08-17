package com.sukaidev.order.data.protocol

/**
 * Created by sukaidev on 2019/08/17.
 * 订单中的商品实体.
 */
data class OrderGoods(
    val id: Int,
    var goodsId: Int,
    val goodsDesc: String,
    val goodsIcon: String,
    val goodsPrice: Long,
    val goodsCount: Int,
    val goodsSku: String,
    val orderId: Int
)