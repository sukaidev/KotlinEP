package com.sukaidev.goods.service.impl

import com.sukaidev.core.ext.convert
import com.sukaidev.goods.data.repository.CategoryRepository
import com.sukaidev.goods.data.protocol.Category
import com.sukaidev.goods.service.CategoryService
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/14.
 *
 */
class CategoryServiceImpl @Inject constructor() : CategoryService {

    @Inject
    lateinit var repository: CategoryRepository

    override fun getCategory(parentId: Int): Observable<MutableList<Category>?> {
        return repository.getCategory(parentId).convert()
    }
}