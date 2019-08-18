package com.sukaidev.order.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.order.data.protocol.ShipAddress
import com.sukaidev.order.presenter.view.IEditShipAddressView
import com.sukaidev.order.service.IShipAddressService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/18.
 *
 */
class EditShipAddressPresenter @Inject constructor() : BasePresenter<IEditShipAddressView>() {

    @Inject
    lateinit var service: IShipAddressService

    /**
     * 添加收货地址
     */
    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String) {
        if (!checkNetWork()) {
            return
        } else {
            mView.showLoading()
            service.addShipAddress(shipUserName, shipUserMobile, shipAddress)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onAddShipAddressResult(t)
                    }
                }, lifecycleProvider)
        }
    }

    /**
     * 删除收货地址
     */
    fun deleteShipAddress(id: Int) {
        if (!checkNetWork()) {
            return
        } else {
            mView.showLoading()
            service.deleteShipAddress(id)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onDeleteShipAddressResult(t)
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
            mView.showLoading()
            service.editShipAddress(address)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onEditShipAddress(t)
                    }
                }, lifecycleProvider)
        }
    }
}