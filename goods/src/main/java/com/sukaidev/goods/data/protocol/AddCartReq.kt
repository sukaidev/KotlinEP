package com.sukaidev.goods.data.protocol

/**
 * Created by sukaidev on 2019/08/16.
 * 购物车添加商品请求体实体.
 */
data class AddCartReq(
    val goodsId: Int, //商品ID
    val goodsTitle:String, // 商品标题
    val goodsDesc: String, //商品描述
    val goodsIcon: String, //商品图标
    val goodsPrice: Long, //商品价格
    val goodsCount: Int, //商品数量
    val goodsSku: String //商品SKU
)