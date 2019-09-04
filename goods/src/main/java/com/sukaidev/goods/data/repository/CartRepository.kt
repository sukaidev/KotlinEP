package com.sukaidev.goods.data.repository

import com.sukaidev.core.data.net.RetrofitFactory
import com.sukaidev.core.data.protocol.BaseResp
import com.sukaidev.goods.data.api.CartApi
import com.sukaidev.goods.data.protocol.AddCartReq
import com.sukaidev.goods.data.protocol.CartGoods
import com.sukaidev.goods.data.protocol.DeleteCartReq
import com.sukaidev.goods.data.protocol.SubmitCartReq
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/16.
 * 购物车数据层.
 */
class CartRepository @Inject constructor() {

    /**
     * 获取购物车列表
     */
    fun getCartList(): Observable<BaseResp<MutableList<CartGoods>?>> {
        return RetrofitFactory.instance.create(CartApi::class.java).getCartList()
    }

    /**
     * 添加商品到购物车
     */
    fun addCart(
        goodsId: Int, goodsTitle: String, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
        goodsCount: Int, goodsSku: String
    ): Observable<BaseResp<Int>> {
        return RetrofitFactory.instance.create(CartApi::class.java)
            .addCart(AddCartReq(goodsId, goodsTitle, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku))
    }

    /**
     * 删除购物车商品
     */
    fun deleteCartList(list: List<Int>): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(CartApi::class.java)
            .deleteCartList(DeleteCartReq(list))
    }

    /**
     * 提交购物车商品
     */
    fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<BaseResp<Int>> {
        return RetrofitFactory.instance.create(CartApi::class.java)
            .submitCart(SubmitCartReq(list, totalPrice))
    }
}