package com.sukaidev.goods.service

import com.sukaidev.goods.data.protocol.Category
import rx.Observable

/**
 * Created by sukaidev on 2019/08/14.
 * 分类信息M层接口.
 */
interface ICategoryService {

    fun getCategory(parentId: Int): Observable<MutableList<Category>?>
}