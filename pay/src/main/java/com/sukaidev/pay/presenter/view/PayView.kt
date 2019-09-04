package com.sukaidev.pay.presenter.view

import com.sukaidev.core.presenter.view.BaseView

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
interface PayView : BaseView {

    fun onGetPaySignResult(result:String)

    fun onPayOrderResult(result:Boolean)
}