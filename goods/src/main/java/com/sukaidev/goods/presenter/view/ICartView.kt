package com.sukaidev.goods.presenter.view

import com.sukaidev.common.presenter.view.BaseView
import com.sukaidev.goods.data.protocol.CartGoods

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
interface ICartView : BaseView {
    /**
     * 获取购物车商品列表
     */
    fun onGetCartList(result: MutableList<CartGoods>?)
}