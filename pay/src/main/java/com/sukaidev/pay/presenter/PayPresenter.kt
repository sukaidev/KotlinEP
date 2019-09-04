package com.sukaidev.pay.presenter

import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.pay.presenter.view.PayView
import com.sukaidev.pay.service.PayService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
class PayPresenter @Inject constructor() : BasePresenter<PayView>() {

    @Inject
    lateinit var service: PayService

    /**
     * 获取支付签名
     */
    fun getPaySign(orderId: Int, totalPrice: Long) {
        if (!checkNetWork()) {
            return
        }
//        mView.showLoading()
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
//        mView.showLoading()
        service.payOrder(orderId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onPayOrderResult(t)
            }
        }, lifecycleProvider)

    }
}