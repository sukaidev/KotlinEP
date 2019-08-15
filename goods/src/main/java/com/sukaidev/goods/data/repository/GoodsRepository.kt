package com.sukaidev.goods.data.repository

import com.sukaidev.common.data.net.RetrofitFactory
import com.sukaidev.common.data.protocol.BaseResp
import com.sukaidev.common.ext.convert
import com.sukaidev.goods.data.api.GoodsApi
import com.sukaidev.goods.data.protocol.GetCategoryReq
import com.sukaidev.goods.data.protocol.Category
import com.sukaidev.goods.data.protocol.GetGoodsListReq
import com.sukaidev.goods.data.protocol.Goods
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/15.
 *
 */
class GoodsRepository @Inject constructor() {

    /**
     * 获取商品列表
     */
    fun getGoodsList(categoryId: Int, pageNo: Int): Observable<BaseResp<MutableList<Goods>?>> {
        return RetrofitFactory.instance.create(GoodsApi::class.java)
            .getGoodsList(GetGoodsListReq(categoryId, pageNo))
    }
}