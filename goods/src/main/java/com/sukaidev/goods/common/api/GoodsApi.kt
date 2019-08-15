package com.sukaidev.goods.common.api

import com.sukaidev.common.data.protocol.BaseResp
import com.sukaidev.goods.common.protocol.GetCategoryReq
import com.sukaidev.goods.data.protocol.Category
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * Created by sukaidev on 2019/08/15.
 * 商品相关Api.
 */
interface GoodsApi {

    @POST("category/getCategory")
    fun getCategory(@Body req: GetCategoryReq): Observable<BaseResp<MutableList<Category>?>>

}