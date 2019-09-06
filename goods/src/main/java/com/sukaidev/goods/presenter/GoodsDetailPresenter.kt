package com.sukaidev.goods.presenter

import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.goods.data.protocol.Goods
import com.sukaidev.goods.presenter.view.GoodsDetailView
import com.sukaidev.goods.service.GoodsService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
class GoodsDetailPresenter @Inject constructor() : BasePresenter<GoodsDetailView>() {

    @Inject
    lateinit var goodsService: GoodsService

    fun getGoodsDetail(goodsId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        goodsService.getGoodsDetail(goodsId).execute(object : BaseSubscriber<Goods>(mView) {
            override fun onNext(t: Goods) {
                mView.onGetGoodsDetail(t)
            }
        }, lifecycleProvider)
    }
}