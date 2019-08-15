package com.sukaidev.goods.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.goods.data.protocol.Category
import com.sukaidev.goods.presenter.view.ICategoryView
import com.sukaidev.goods.service.ICategoryService
import com.sukaidev.goods.service.impl.CategoryServiceImpl
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/14.
 *
 */
class CategoryPresenter @Inject constructor() : BasePresenter<ICategoryView>() {

    @Inject
    lateinit var categoryService: ICategoryService

    fun getCategory(parentId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        categoryService.getCategory(parentId)
            .execute(object : BaseSubscriber<MutableList<Category>?>(mView) {
                override fun onNext(t: MutableList<Category>?) {
                    mView.onGetCategoryResult(t)
                }
            }, lifecycleProvider)
    }
}