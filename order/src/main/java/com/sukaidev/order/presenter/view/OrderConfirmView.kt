package com.sukaidev.order.presenter.view

import com.sukaidev.core.presenter.view.BaseView
import com.sukaidev.order.data.protocol.Order

/**
 * Created by sukaidev on 2019/08/17.
 *
 */
interface OrderConfirmView : BaseView {

    fun onGetOrderByIdResult(result: Order)

    fun onSubmitOrderResult(result: String)
}