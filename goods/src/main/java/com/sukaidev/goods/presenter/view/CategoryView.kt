package com.sukaidev.goods.presenter.view

import com.sukaidev.core.presenter.view.BaseView
import com.sukaidev.goods.data.protocol.Category

/**
 * Created by sukaidev on 2019/08/14.
 * V层回调.
 */
interface CategoryView : BaseView {

    fun onGetCategoryResult(result:MutableList<Category>?)
}