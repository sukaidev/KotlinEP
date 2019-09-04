package com.sukaidev.order.presenter

import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.order.data.protocol.ShipAddress
import com.sukaidev.order.presenter.view.ShipAddressView
import com.sukaidev.order.service.ShipAddressService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/18.
 *
 */
class ShipAddressPresenter @Inject constructor() : BasePresenter<ShipAddressView>() {

    @Inject
    lateinit var shipAddressService: ShipAddressService

    /**
     * 获取收货地址列表
     */
    fun getShipAddressList() {
        if (!checkNetWork()) {
            return
        } else {
            shipAddressService.getShipAddressList()
                .execute(object : BaseSubscriber<MutableList<ShipAddress>?>(mView) {
                    override fun onNext(t: MutableList<ShipAddress>?) {
                        mView.onGetShipAddressListResult(t)
                    }
                }, lifecycleProvider)
        }
    }

    /**
     * 设置默认地址
     */
    fun setDefaultAddress(address: ShipAddress) {
        if (!checkNetWork()) {
            return
        }
        shipAddressService.editShipAddress(address).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onSetDefaultResult(t)
            }
        }, lifecycleProvider)
    }

    /**
     * 删除地址
     */
    fun deleteShipAddress(addressId: Int) {
        if (!checkNetWork()) {
            return
        }
        shipAddressService.deleteShipAddress(addressId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onDeleteDefaultResult(t)
            }
        }, lifecycleProvider)
    }
}