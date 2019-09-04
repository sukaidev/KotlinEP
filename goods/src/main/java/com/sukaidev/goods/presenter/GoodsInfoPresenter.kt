package com.sukaidev.goods.presenter

import com.sukaidev.core.common.GoodsConstant
import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.core.utils.AppPrefsUtils
import com.sukaidev.goods.presenter.view.GoodsInfoView
import com.sukaidev.goods.service.CartService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/09/03.
 *
 */
class GoodsInfoPresenter @Inject constructor() : BasePresenter<GoodsInfoView>() {

    @Inject
    lateinit var cartService: CartService

    /**
     * 添加商品到购物车
     */
    fun addCart(
        goodsId: Int, goodsTitle: String, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
        goodsCount: Int, goodsSku: String
    ) {
        if (!checkNetWork()) {
            return
        }
        cartService.addCart(goodsId, goodsTitle, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku)
            .execute(object : BaseSubscriber<Int>(mView) {
                override fun onNext(t: Int) {
                    AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE, t)
                    mView.onAddCartResult(t)
                }
            }, lifecycleProvider)
    }
}