package com.sukaidev.order.presenter.view

import com.sukaidev.common.presenter.view.BaseView
import com.sukaidev.order.data.protocol.Order

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
interface IOrderDetailView : BaseView {

    fun onGetOrderByIdResult(result: Order)
}