package com.sukaidev.goods.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
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
     * 添加商品到购物车
     */
    fun addCart(
        goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
        goodsCount: Int, goodsSku: String
    ) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        service.addCart(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku)
            .execute(object : BaseSubscriber<Int>(mView) {
                override fun onNext(t: Int) {
                    mView.onAddCartResult(t)
                }
            }, lifecycleProvider)
    }
}