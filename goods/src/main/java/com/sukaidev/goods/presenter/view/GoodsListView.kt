package com.sukaidev.goods.presenter.view

import com.sukaidev.core.presenter.view.BaseView
import com.sukaidev.goods.data.protocol.Goods

/**
 * Created by sukaidev on 2019/08/14.
 * 商品列表回调.
 */
interface GoodsListView : BaseView {

    fun onGetGoodsListResult(result:MutableList<Goods>?)
}