package com.sukaidev.goods.presenter.view

import com.sukaidev.common.presenter.view.BaseView
import com.sukaidev.goods.data.protocol.Category

/**
 * Created by sukaidev on 2019/08/14.
 * V层回调.
 */
interface ICategoryView :BaseView{

    fun onGetCategoryResult(result:MutableList<Category>?)
}