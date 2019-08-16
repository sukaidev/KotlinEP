package com.sukaidev.goods.data.protocol

/**
 * Created by sukaidev on 2019/08/16.
 * 删除购物车商品请求实体.
 */
data class DeleteCartReq(val cartIdList: List<Int> = arrayListOf())