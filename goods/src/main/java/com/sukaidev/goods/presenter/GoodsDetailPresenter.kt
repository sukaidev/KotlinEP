package com.sukaidev.goods.presenter

import com.sukaidev.core.common.GoodsConstant
import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.core.utils.AppPrefsUtils
import com.sukaidev.goods.data.protocol.Goods
import com.sukaidev.goods.presenter.view.GoodsDetailView
import com.sukaidev.goods.service.CartService
import com.sukaidev.goods.service.GoodsService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
class GoodsDetailPresenter @Inject constructor() : BasePresenter<GoodsDetailView>() {

    @Inject
    lateinit var goodsService: GoodsService

    @Inject
    lateinit var cartService: CartService

    fun getGoodsDetail(goodsId: Int) {
        if (!checkNetWork()) {
            return
        }
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
        cartService.addCart(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku)
            .execute(object : BaseSubscriber<Int>(mView) {
                override fun onNext(t: Int) {
                    AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE,t)
                    mView.onAddCartResult(t)
                }
            }, lifecycleProvider)
    }
}