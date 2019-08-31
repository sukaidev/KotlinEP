package com.sukaidev.goods.presenter.view

import com.sukaidev.core.presenter.view.BaseView
import com.sukaidev.goods.data.protocol.CartGoods

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
interface CartView : BaseView {
    /**
     * 获取购物车商品列表回调
     */
    fun onGetCartListResult(result: MutableList<CartGoods>?)

    /**
     * 删除购物车商品回调
     */
    fun onDeleteCartListResult(result: Boolean)

    /**
     * 提交购物车回调
     */
    fun onSubmitCartResult(result: Int)

}