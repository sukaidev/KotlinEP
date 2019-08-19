package com.sukaidev.order.presenter.view

import com.sukaidev.common.presenter.view.BaseView
import com.sukaidev.order.data.protocol.Order

/**
 * Created by sukaidev on 2019/08/18.
 *
 */
interface IOrderListView : BaseView {

    fun onGetOrderListResult(result: MutableList<Order>?)
    fun onConfirmOrderResult(result: Boolean)
    fun onCancelOrderResult(result: Boolean)
}