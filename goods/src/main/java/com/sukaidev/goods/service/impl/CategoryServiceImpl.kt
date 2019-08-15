package com.sukaidev.goods.service.impl

import com.sukaidev.common.ext.convert
import com.sukaidev.goods.common.repository.CategoryRepository
import com.sukaidev.goods.data.protocol.Category
import com.sukaidev.goods.service.ICategoryService
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/14.
 *
 */
class CategoryServiceImpl @Inject constructor() : ICategoryService {

    @Inject
    lateinit var repository: CategoryRepository

    override fun getCategory(parentId: Int): Observable<MutableList<Category>?> {
        return repository.getCategory(parentId).convert()
    }
}