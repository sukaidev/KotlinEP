package com.sukaidev.goods.data.protocol

/**
 * Created by sukaidev on 2019/08/16.
 * 购物车商品实体类.
 */
data class CartGoods(
    val id: Int,//购物车单项商品ID
    val goodsId:Int,//具体商品ID
    val goodsTitle:String, // 商品标题
    val goodsDesc: String,//商品描述
    val goodsIcon: String,//商品图片
    val goodsPrice: Long,//商品价格
    var goodsCount: Int,//商品数量
    val goodsSku:String,//商品SKU
    var isSelected:Boolean//是否选中
)