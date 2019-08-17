package com.sukaidev.order.data.protocol

/**
 * Created by sukaidev on 2019/08/17.
 * 订单实体.
 * @param id 订单ID
 * @param payType 支付类型
 * @param shipAddress 收货地址
 * @param totalPrice 总价
 * @param orderStatus 订单状态
 * @param orderGoodsList 订单商品列表
 */
data class Order(
    val id: Int,
    val payType: Int,
    var shipAddress: ShipAddress?,
    val totalPrice: Long,
    var orderStatus: Int,
    val orderGoodsList: MutableList<OrderGoods>
)