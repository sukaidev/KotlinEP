package com.sukaidev.goods.service.impl

import com.sukaidev.core.ext.convert
import com.sukaidev.goods.data.protocol.Goods
import com.sukaidev.goods.data.repository.GoodsRepository
import com.sukaidev.goods.service.GoodsService
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/15.
 *
 */
class GoodsServiceImpl @Inject constructor() : GoodsService {

    @Inject
    lateinit var repository: GoodsRepository

    /**
     * 获取商品列表
     */
    override fun getGoodsList(categoryId: Int, pageNo: Int): Observable<MutableList<Goods>?> {
        return repository.getGoodsList(categoryId, pageNo).convert()
    }

    /**
     * 通过关键字搜索获取商品列表
     */
    override fun getGoodsListByKeyword(
        keyword: String,
        pageNo: Int
    ): Observable<MutableList<Goods>?> {
        return repository.getGoodsListByKeyword(keyword, pageNo).convert()
    }

    /**
     * 获取商品详情
     */
    override fun getGoodsDetail(goodsId: Int): Observable<Goods> {
        return repository.getGoodsDetail(goodsId).convert()
    }
}