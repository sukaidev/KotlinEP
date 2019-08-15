package com.sukaidev.goods.data.repository

import com.sukaidev.common.data.net.RetrofitFactory
import com.sukaidev.common.data.protocol.BaseResp
import com.sukaidev.goods.data.api.CategoryApi
import com.sukaidev.goods.data.api.GoodsApi
import com.sukaidev.goods.data.protocol.GetCategoryReq
import com.sukaidev.goods.data.protocol.Category
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/15.
 *
 */
class CategoryRepository @Inject constructor() {

    fun getCategory(
        parentId:Int
    ): Observable<BaseResp<MutableList<Category>?>>  {
        return RetrofitFactory.instance.create(CategoryApi::class.java).getCategory(
            GetCategoryReq(
                parentId
            )
        )
    }
}