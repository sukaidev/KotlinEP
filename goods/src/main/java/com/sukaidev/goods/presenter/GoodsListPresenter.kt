package com.sukaidev.goods.presenter

import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.goods.data.protocol.Goods
import com.sukaidev.goods.presenter.view.GoodsListView
import com.sukaidev.goods.service.GoodsService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/15.
 *
 */
class GoodsListPresenter @Inject constructor() : BasePresenter<GoodsListView>() {

    @Inject
    lateinit var goodsService: GoodsService

    /**
     * 获取商品列表
     */
    fun getGoodsList(categoryId: Int, noPage: Int) {
        if (!checkNetWork()) {
            return
        }
//        mView.showLoading()
        goodsService.getGoodsList(categoryId, noPage)
            .execute(object : BaseSubscriber<MutableList<Goods>?>(mView) {
                override fun onNext(t: MutableList<Goods>?) {
                    mView.onGetGoodsListResult(t)
                }
            }, lifecycleProvider)
    }

    fun getGoodsListByKeyword(keyword: String, noPage: Int) {
        if (!checkNetWork()) {
            return
        }
//        mView.showLoading()
        goodsService.getGoodsListByKeyword(keyword, noPage)
            .execute(object : BaseSubscriber<MutableList<Goods>?>(mView) {
                override fun onNext(t: MutableList<Goods>?) {
                    mView.onGetGoodsListResult(t)
                }
            }, lifecycleProvider)
    }
}