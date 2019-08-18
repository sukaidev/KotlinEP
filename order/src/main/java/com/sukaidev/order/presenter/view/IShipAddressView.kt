package com.sukaidev.order.presenter.view

import com.sukaidev.common.presenter.view.BaseView
import com.sukaidev.order.data.protocol.ShipAddress

/**
 * Created by sukaidev on 2019/08/18.
 *
 */
interface IShipAddressView:BaseView {
    fun onGetShipAddressListResult(result: MutableList<ShipAddress>?)
}