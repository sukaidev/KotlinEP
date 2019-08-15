package com.sukaidev.goods.service.impl

import com.sukaidev.common.data.net.RetrofitFactory
import com.sukaidev.common.ext.convert
import com.sukaidev.goods.data.api.GoodsApi
import com.sukaidev.goods.data.protocol.GetGoodsListReq
import com.sukaidev.goods.data.protocol.Goods
import com.sukaidev.goods.data.repository.GoodsRepository
import com.sukaidev.goods.service.IGoodsService
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/15.
 *
 */
class GoodsServiceImpl @Inject constructor() : IGoodsService {

    @Inject
    lateinit var repository: GoodsRepository

    /**
     * 获取商品列表
     */
    override fun getGoodsList(categoryId: Int, pageNo: Int): Observable<MutableList<Goods>?> {
        return repository.getGoodsList(categoryId, pageNo).convert()
    }
}