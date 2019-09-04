package com.sukaidev.order.presenter.view

import com.sukaidev.core.presenter.view.BaseView
import com.sukaidev.order.data.protocol.ShipAddress

/**
 * Created by sukaidev on 2019/08/18.
 *
 */
interface ShipAddressView : BaseView {
    fun onGetShipAddressListResult(result: MutableList<ShipAddress>?)
    fun onSetDefaultResult(result: Boolean)
    fun onDeleteDefaultResult(result: Boolean)
}