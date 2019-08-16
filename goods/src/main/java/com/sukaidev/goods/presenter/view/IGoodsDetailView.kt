package com.sukaidev.goods.presenter.view

import com.sukaidev.common.presenter.view.BaseView
import com.sukaidev.goods.data.protocol.Goods

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
interface IGoodsDetailView : BaseView {

    fun onGetGoodsDetail(result:Goods)
}