package com.sukaidev.goods.data.protocol

/**
 * Created by sukaidev on 2019/08/15.
 * 按关键字搜索商品.
 */
data class GetGoodsListByKeywordReq(val keyword: String, val pageNo: Int)