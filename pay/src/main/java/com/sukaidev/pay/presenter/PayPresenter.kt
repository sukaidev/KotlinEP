package com.sukaidev.pay.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.pay.presenter.view.IPayView
import com.sukaidev.pay.service.IPayService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
class PayPresenter @Inject constructor() : BasePresenter<IPayView>() {

    @Inject
    lateinit var service: IPayService

    /**
     * 获取支付签名
     */
    fun getPaySign(orderId: Int, totalPrice: Long) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        service.getPaySign(orderId, totalPrice).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onGetPaySignResult(t)
            }
        }, lifecycleProvider)
    }

    /**
     * 支付订单并同步订单状态
     */
    fun payOrder(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        service.payOrder(orderId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onPayOrderResult(t)
            }
        }, lifecycleProvider)

    }
}