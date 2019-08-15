package com.sukaidev.goods.data.protocol

/**
 * Created by sukaidev on 2019/08/15.
 * 获取分类列表请求，parentId为0是一级分类.
 */
data class GetCategoryReq (val parentId: Int)
