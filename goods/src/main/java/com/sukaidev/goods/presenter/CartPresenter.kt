package com.sukaidev.goods.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.goods.data.protocol.CartGoods
import com.sukaidev.goods.presenter.view.ICartView
import com.sukaidev.goods.service.ICartService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
class CartPresenter @Inject constructor() : BasePresenter<ICartView>() {

    @Inject
    lateinit var service: ICartService

    /**
     * 获取购物车商品列表
     */
    fun getCartList() {
        if (!checkNetWork()) {
            return
        }
        service.getCartList()
            .execute(object : BaseSubscriber<MutableList<CartGoods>?>(mView) {
                override fun onNext(t: MutableList<CartGoods>?) {
                    mView.onGetCartList(t)
                }
            }, lifecycleProvider)
    }

    fun deleteCartList(deleteCartList: List<Int>) {
        if (!checkNetWork()) {
            return
        }
        service.deleteCartList(deleteCartList)
            .execute(object : BaseSubscriber<String>(mView) {
                override fun onNext(t: String) {

                }
            },lifecycleProvider)
    }


}