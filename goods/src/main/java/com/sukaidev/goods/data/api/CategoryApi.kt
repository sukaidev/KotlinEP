package com.sukaidev.goods.data.api

import com.sukaidev.common.data.protocol.BaseResp
import com.sukaidev.goods.data.protocol.Category
import com.sukaidev.goods.data.protocol.GetCategoryReq
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * Created by sukaidev on 2019/08/15.
 * 分类信息Api.
 */
/**
 * 获取分类信息
 */
interface CategoryApi {
    /**
     * 获取分类信息
     */
    @POST("category/getCategory")
    fun getCategory(@Body req: GetCategoryReq): Observable<BaseResp<MutableList<Category>?>>
}