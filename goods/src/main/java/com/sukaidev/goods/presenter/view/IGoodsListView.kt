package com.sukaidev.goods.presenter.view

import com.sukaidev.common.presenter.view.BaseView
import com.sukaidev.goods.data.protocol.Category
import com.sukaidev.goods.data.protocol.Goods

/**
 * Created by sukaidev on 2019/08/14.
 * 商品列表回调.
 */
interface IGoodsListView :BaseView{

    fun onGetGoodsListResult(result:MutableList<Goods>?)
}