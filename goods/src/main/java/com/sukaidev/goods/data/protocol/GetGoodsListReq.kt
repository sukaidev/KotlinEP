package com.sukaidev.goods.data.protocol

/**
 * Created by sukaidev on 2019/08/15.
 * 按分类和分页搜索商品.
 */
data class GetGoodsListReq(val categoryId: Int,val pageNo: Int)