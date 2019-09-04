package com.sukaidev.order.presenter.view

import com.sukaidev.core.presenter.view.BaseView

/**
 * Created by sukaidev on 2019/08/18.
 *
 */
interface EditShipAddressView : BaseView {

    fun onAddShipAddressResult(result: Boolean)

    fun onEditShipAddress(result: Boolean)
}