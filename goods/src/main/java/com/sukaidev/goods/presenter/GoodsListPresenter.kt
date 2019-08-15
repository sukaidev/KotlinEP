package com.sukaidev.goods.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.goods.data.protocol.Goods
import com.sukaidev.goods.presenter.view.IGoodsListView
import com.sukaidev.goods.service.IGoodsService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/15.
 *
 */
class GoodsListPresenter @Inject constructor() : BasePresenter<IGoodsListView>() {

    @Inject
    lateinit var goodsService: IGoodsService

    fun getGoodsList(categoryId: Int, noPage: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        goodsService.getGoodsList(categoryId, noPage)
            .execute(object : BaseSubscriber<MutableList<Goods>?>(mView) {
                override fun onNext(t: MutableList<Goods>?) {
                    mView.onGetGoodsListResult(t)
                }
            }, lifecycleProvider)
    }
}