package com.sukaidev.goods.service

import com.sukaidev.goods.data.protocol.Goods
import rx.Observable

/**
 * Created by sukaidev on 2019/08/15.
 * 商品信息M层接口.
 */
interface IGoodsService {

    /**
     * 获取商品列表
     */
    fun getGoodsList(categoryId:Int,pageNo:Int):Observable<MutableList<Goods>?>
}