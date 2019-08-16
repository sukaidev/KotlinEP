package com.sukaidev.goods.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.common.utils.AppPrefsUtils
import com.sukaidev.goods.common.GoodsConstant
import com.sukaidev.goods.data.protocol.Goods
import com.sukaidev.goods.presenter.view.IGoodsDetailView
import com.sukaidev.goods.service.ICartService
import com.sukaidev.goods.service.IGoodsService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
class GoodsDetailPresenter @Inject constructor() : BasePresenter<IGoodsDetailView>() {

    @Inject
    lateinit var goodsService: IGoodsService

    @Inject
    lateinit var cartService: ICartService

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
        cartService.addCart(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku)
            .execute(object : BaseSubscriber<Int>(mView) {
                override fun onNext(t: Int) {
                    AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE,t)
                    mView.onAddCartResult(t)
                }
            }, lifecycleProvider)
    }
}