package com.sukaidev.order.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.order.data.protocol.ShipAddress
import com.sukaidev.order.presenter.view.IShipAddressView
import com.sukaidev.order.service.IShipAddressService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/18.
 *
 */
class ShipAddressPresenter @Inject constructor() : BasePresenter<IShipAddressView>() {

    @Inject
    lateinit var service: IShipAddressService

    /**
     * 获取收货地址列表
     */
    fun getShipAddressList() {
        if (!checkNetWork()) {
            return
        } else {
            mView.showLoading()
            service.getShipAddressList()
                .execute(object : BaseSubscriber<MutableList<ShipAddress>?>(mView) {
                    override fun onNext(t: MutableList<ShipAddress>?) {
                        mView.onGetShipAddressListResult(t)
                    }
                }, lifecycleProvider)
        }
    }
}