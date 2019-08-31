package com.sukaidev.goods.presenter

import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.goods.data.protocol.Category
import com.sukaidev.goods.presenter.view.CategoryView
import com.sukaidev.goods.service.CategoryService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/14.
 *
 */
class CategoryPresenter @Inject constructor() : BasePresenter<CategoryView>() {

    @Inject
    lateinit var categoryService: CategoryService

    fun getCategory(parentId: Int) {
        if (!checkNetWork()) {
            return
        }
        categoryService.getCategory(parentId)
            .execute(object : BaseSubscriber<MutableList<Category>?>(mView) {
                override fun onNext(t: MutableList<Category>?) {
                    mView.onGetCategoryResult(t)
                }
            }, lifecycleProvider)
    }
}