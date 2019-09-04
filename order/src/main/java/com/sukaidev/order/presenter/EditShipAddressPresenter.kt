package com.sukaidev.order.presenter

import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.order.data.protocol.ShipAddress
import com.sukaidev.order.presenter.view.EditShipAddressView
import com.sukaidev.order.service.ShipAddressService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/18.
 *
 */
class EditShipAddressPresenter @Inject constructor() : BasePresenter<EditShipAddressView>() {

    @Inject
    lateinit var service: ShipAddressService

    /**
     * 添加收货地址
     */
    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String) {
        if (!checkNetWork()) {
            return
        } else {
            service.addShipAddress(shipUserName, shipUserMobile, shipAddress)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onAddShipAddressResult(t)
                    }
                }, lifecycleProvider)
        }
    }

    /**
     * 修改收货地址
     */
    fun editShipAddress(address: ShipAddress) {
        if (!checkNetWork()) {
            return
        } else {
            service.editShipAddress(address)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onEditShipAddress(t)
                    }
                }, lifecycleProvider)
        }
    }
}