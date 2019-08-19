package com.sukaidev.pay.presenter.view

import com.sukaidev.common.presenter.view.BaseView

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
interface IPayView :BaseView {

    fun onGetPaySignResult(result:String)

    fun onPayOrderResult(result:Boolean)
}