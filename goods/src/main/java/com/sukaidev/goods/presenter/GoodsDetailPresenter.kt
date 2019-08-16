package com.sukaidev.goods.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.goods.data.protocol.Goods
import com.sukaidev.goods.presenter.view.IGoodsDetailView
import com.sukaidev.goods.service.IGoodsService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
class GoodsDetailPresenter @Inject constructor() : BasePresenter<IGoodsDetailView>() {

    @Inject
    lateinit var service: IGoodsService

    fun getGoodsDetail(goodsId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        service.getGoodsDetail(goodsId).execute(object : BaseSubscriber<Goods>(mView) {
            override fun onNext(t: Goods) {
                mView.onGetGoodsDetail(t)
            }
        }, lifecycleProvider)
    }
}