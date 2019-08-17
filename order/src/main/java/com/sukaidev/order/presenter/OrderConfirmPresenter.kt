package com.sukaidev.order.presenter

import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.order.presenter.view.IOrderConfirmView
import com.sukaidev.order.service.IOrderService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/17.
 *
 */
class OrderConfirmPresenter @Inject constructor() : BasePresenter<IOrderConfirmView>() {

    @Inject
    lateinit var service: IOrderService


}