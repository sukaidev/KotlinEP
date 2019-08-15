package com.sukaidev.goods.data.api

import com.sukaidev.common.data.protocol.BaseResp
import com.sukaidev.goods.data.protocol.GetCategoryReq
import com.sukaidev.goods.data.protocol.Category
import com.sukaidev.goods.data.protocol.GetGoodsListReq
import com.sukaidev.goods.data.protocol.Goods
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * Created by sukaidev on 2019/08/15.
 * 商品相关Api.
 */
interface GoodsApi {

    /**
     * 获取商品列表
     */
    @POST("goods/getGoodsList")
    fun getGoodsList(@Body req: GetGoodsListReq): Observable<BaseResp<MutableList<Goods>?>>
}